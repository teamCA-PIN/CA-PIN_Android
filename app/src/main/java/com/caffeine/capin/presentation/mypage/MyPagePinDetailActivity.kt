package com.caffeine.capin.presentation.mypage

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.caffeine.capin.presentation.customview.CapinDialog
import com.caffeine.capin.presentation.customview.CapinDialogBuilder
import com.caffeine.capin.presentation.customview.DialogClickListener
import com.caffeine.capin.databinding.ActivityMyPagePinDetailBinding
import com.caffeine.capin.data.dto.mypage.RequestDeletePinData
import com.caffeine.capin.data.dto.mypage.ResponseMyPinData
import com.caffeine.capin.data.network.BaseResponse
import com.caffeine.capin.data.network.ServiceCreator
import com.caffeine.capin.data.local.UserPreferenceManager
import com.caffeine.capin.data.dto.mypage.MyPinInfo
import com.caffeine.capin.presentation.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MyPagePinDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyPagePinDetailBinding
    private lateinit var myPinInfoAdapter: MyPinInfoAdapter
    private var removePinInfoList: MutableList<MyPinInfo> = mutableListOf()
    private var removePinId: MutableList<String> = mutableListOf()

    @Inject
    lateinit var userPreferenceManager: UserPreferenceManager

    lateinit var categoryPinId: String
    private val success = MutableLiveData<UiState<Boolean>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPagePinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        categoryPinId = intent.getStringExtra("categoryPinId").toString()
        binding.pinDetailBackBtn.setOnClickListener { onBackPressed() }

        setFeature()
        setMyPinInfoAdapter()
        getMyPinFromServer()
        setDefaultComment()
        pinEditButtonClickEvent()
        pinDeleteButtonClickEvent()
        pinDeleteCancelButtonClickEvent()
        displayProgressBar()
    }

    private fun setFeature() {
        if (intent.hasExtra("name")) {
            binding.pinDetailHeaderTv.text = intent.getStringExtra("name")
        } else {
            binding.pinDetailHeaderTv.text = "카테고리"
        }
    }

    private fun setDefaultComment() {
        if (myPinInfoAdapter.myPinInfoList.size > 1) {
            binding.pinDetailBasicIv.isVisible = false
            binding.pinDetailBasicTv.isVisible = false
        }
    }

    private fun setMyPinInfoAdapter() {
        myPinInfoAdapter = MyPinInfoAdapter()
        binding.pinDetailRcv.adapter = myPinInfoAdapter

        myPinInfoAdapter.setOnCheckboxClickListener(object :
            MyPinInfoAdapter.OnCheckboxClickListener {
            override fun onCheckboxClick(myPinInfo: MyPinInfo) {
                if (myPinInfo in removePinInfoList) {
                    removePinInfoList.remove(myPinInfo)
                    if (removePinInfoList.size > 0) {
                        binding.pinDetailHeaderTv.text = "${removePinInfoList.size}개 선택됨"
                    } else {
                        binding.pinDetailInactiveDeleteBtn.isVisible = true
                        binding.pinDetailActiveDeleteBtn.isGone = true
                        binding.pinDetailHeaderTv.text = intent.getStringExtra("name")
                    }
                } else {
                    binding.pinDetailActiveDeleteBtn.isVisible = true
                    binding.pinDetailInactiveDeleteBtn.isGone = true
                    removePinInfoList.add(myPinInfo)
                    binding.pinDetailHeaderTv.text = "${removePinInfoList.size}개 선택됨"
                    removePinId.add(myPinInfo._id)
                }
            }
        })

        val decoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        binding.pinDetailRcv.addItemDecoration(decoration)

        myPinInfoAdapter.notifyDataSetChanged()
        binding.pinDetailNumTv.setText("총 ${myPinInfoAdapter.myPinInfoList.size}개의 핀")
    }

    private fun pinEditButtonClickEvent() {
        binding.pinDetailEditBtn.setOnClickListener {
            myPinInfoAdapter.switchDeleteMode(true)
            binding.pinDetailEditBtn.isGone = true
            binding.pinDetailCancelBtn.isVisible = true
            binding.pinDetailInactiveDeleteBtn.isVisible = true
        }
    }

    private fun pinDeleteButtonClickEvent() {
        binding.pinDetailActiveDeleteBtn.setOnClickListener {
            showDeletePinConfirmDialog()
            binding.pinDetailEditBtn.isVisible = true
            binding.pinDetailActiveDeleteBtn.isGone = true
        }
    }

    private fun pinDeleteCancelButtonClickEvent() {
        binding.pinDetailCancelBtn.setOnClickListener {
            binding.pinDetailCancelBtn.isGone = true
            binding.pinDetailEditBtn.isVisible = true
            binding.pinDetailInactiveDeleteBtn.isGone = true
            binding.pinDetailActiveDeleteBtn.isGone = true
            for (i in 0 until removePinInfoList.size) {
                myPinInfoAdapter.checkboxList.forEach {
                    it.isChecked = false
                }
            }
            myPinInfoAdapter.checkboxList.clear()
            removePinInfoList.clear()
            myPinInfoAdapter.switchDeleteMode(false)
            myPinInfoAdapter.notifyDataSetChanged()
            binding.pinDetailNumTv.setText("총 ${myPinInfoAdapter.myPinInfoList.size}개의 핀")
            binding.pinDetailHeaderTv.text = intent.getStringExtra("name")
        }
    }

    private fun showDeletePinConfirmDialog() {
        val dialog: CapinDialog = CapinDialogBuilder(null)
            .setContentDialogTitile("선택된 핀을 모두 삭제하시겠습니까?")
            .setContentDialogButtons(true, object : DialogClickListener {
                override fun onClick() {

                    deleteMyPinAtServer()

                    for (i in 0 until removePinInfoList.size) {
                        myPinInfoAdapter.myPinInfoList.remove(removePinInfoList[i])
                        myPinInfoAdapter.checkboxList.forEach {
                            it.isChecked = false
                        }
                    }
                    myPinInfoAdapter.checkboxList.clear()
                    removePinInfoList.clear()
                    myPinInfoAdapter.switchDeleteMode(false)
                    myPinInfoAdapter.notifyDataSetChanged()
                    binding.pinDetailNumTv.setText("총 ${myPinInfoAdapter.myPinInfoList.size}개의 핀")
                    binding.pinDetailHeaderTv.text = intent.getStringExtra("name")
                }
            }).build()
        dialog.show(supportFragmentManager, "DeleteReview")
    }

    private fun getMyPinFromServer() {
        val capinApiService = ServiceCreator.capinApiService.getMyPin(
            userPreferenceManager.getUserAccessToken(),
            categoryId = categoryPinId
        )

        success.value = UiState.loading(false)
        capinApiService.enqueue(object : Callback<ResponseMyPinData> {
            override fun onFailure(call: Call<ResponseMyPinData>, t: Throwable) {
                Log.d("fail", "error:$t")
                success.value = UiState.error(true, t.message)
            }

            override fun onResponse(
                call: Call<ResponseMyPinData>,
                response: Response<ResponseMyPinData>
            ) {
                if (response.isSuccessful) {
                    success.value = UiState.success(true)
                    myPinInfoAdapter.myPinInfoList =
                        response.body()?.cafeDetail as MutableList<MyPinInfo>
                    myPinInfoAdapter.notifyDataSetChanged()
                }
                binding.pinDetailNumTv.setText("총 ${myPinInfoAdapter.myPinInfoList.size}개의 핀")

                if (myPinInfoAdapter.myPinInfoList.size > 0) {
                    binding.pinDetailBasicIv.isVisible = false
                    binding.pinDetailBasicTv.isVisible = false
                }
            }
        })
    }

    private fun displayProgressBar() {
        success.observe(this) {
            when(it.status) {
                UiState.Status.LOADING -> {
                    binding.progressbar.visibility = View.VISIBLE
                }
                UiState.Status.SUCCESS -> {
                    binding.progressbar.visibility = View.GONE
                }
                UiState.Status.ERROR -> {
                    binding.progressbar.visibility = View.GONE
                }
            }
        }
    }

    private fun deleteMyPinAtServer() {
        val requestDeletePinData = RequestDeletePinData(
            cafeList = removePinId
        )

        val capinApiService = ServiceCreator.capinApiService.deleteMyPin(
            userPreferenceManager.getUserAccessToken(),
            categoryPinId,
            requestDeletePinData
        )

        capinApiService.enqueue(object : Callback<BaseResponse> {
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d("fail", "error:$t")
            }

            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                getMyPinFromServer()
                myPinInfoAdapter.notifyDataSetChanged()
            }
        })
    }
}