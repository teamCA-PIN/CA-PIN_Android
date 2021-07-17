package com.caffeine.capin.mypage.pin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.caffeine.capin.customview.CapinDialog
import com.caffeine.capin.customview.CapinDialogBuilder
import com.caffeine.capin.customview.DialogClickListener
import com.caffeine.capin.databinding.ActivityMyPagePinDetailBinding
import com.caffeine.capin.mypage.api.request.RequestDeletePinData
import com.caffeine.capin.mypage.api.response.ResponseMyPinData
import com.caffeine.capin.network.BaseResponse
import com.caffeine.capin.network.ServiceCreator
import com.caffeine.capin.preference.UserPreferenceManager
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPagePinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryPinId = intent.getStringExtra("categoryPinId").toString()
        binding.pinDetailBackBtn.setOnClickListener { onBackPressed() }

        setFeature()
        setMyPinInfoAdapter()
        getMyPinFromServer()
        setDefaultComment()
        pinEditButtonClickEvent()
        pinDeleteButtonClickEvent()
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
                        myPinInfoAdapter.switchDeleteMode(false)
                        binding.pinDetailEditBtn.isVisible = true
                        binding.pinDetailActiveDeleteBtn.isGone = true
                        binding.pinDetailInactiveDeleteBtn.isGone = true
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
            userPreferenceManager.getUserToken(),
            categoryId = categoryPinId
        )

        capinApiService.enqueue(object : Callback<ResponseMyPinData> {
            override fun onFailure(call: Call<ResponseMyPinData>, t: Throwable) {
                Log.d("fail", "error:$t")
            }

            override fun onResponse(
                call: Call<ResponseMyPinData>,
                response: Response<ResponseMyPinData>
            ) {
                if (response.isSuccessful) {
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

    private fun deleteMyPinAtServer() {
        val requestDeletePinData = RequestDeletePinData(
            cafeList = removePinId
        )

        val capinApiService = ServiceCreator.capinApiService.deleteMyPin(
            userPreferenceManager.getUserToken(),
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