package com.caffeine.capin.presentation.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.caffeine.capin.R
import com.caffeine.capin.databinding.FragmentMyPageReviewBinding
import com.caffeine.capin.presentation.cafedetail.ui.CafeDetailsActivity
import com.caffeine.capin.presentation.cafedetail.ui.CafeDetailsActivity.Companion.KEY_CAFE_ID
import com.caffeine.capin.data.dto.mypage.ResponseMyReviewData
import com.caffeine.capin.data.dto.mypage.MyReview
import com.caffeine.capin.data.network.BaseResponse
import com.caffeine.capin.data.network.ServiceCreator
import com.caffeine.capin.data.local.UserPreferenceManager
import com.caffeine.capin.presentation.customview.CapinDialog
import com.caffeine.capin.presentation.customview.CapinDialogBuilder
import com.caffeine.capin.presentation.customview.CapinDialogButton
import com.caffeine.capin.presentation.customview.DialogClickListener
import com.caffeine.capin.presentation.review.ui.WriteReviewActivity
import com.caffeine.capin.presentation.review.ui.WriteReviewActivity.Companion.EDIT_REVIEW
import com.caffeine.capin.presentation.util.AutoClearedValue
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MyPageReviewFragment : Fragment() {
    @Inject lateinit var userPreferenceManager: UserPreferenceManager
    private var binding by AutoClearedValue<FragmentMyPageReviewBinding>()
    private lateinit var myReviewAdapter: MyReviewAdapter
    private lateinit var removeReviewInfo: MyReview

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMyReviewAdapter()
        getMyReviewFromServer()
    }

    private fun setMyReviewAdapter() {
        myReviewAdapter = MyReviewAdapter()
        binding.mypageReviewRcv.adapter = myReviewAdapter

        myReviewAdapter.setOnEditButtonClickListener(object :
            MyReviewAdapter.OnEditButtonClickListener {
            override fun onEditButtonClick(myReview: MyReview) {
                removeReviewInfo = myReview
                showEditReviewDialog()
            }
        })

        myReviewAdapter.setOnDetailButtonClickListener(object :
            MyReviewAdapter.OnDetailButtonClickListener {
            override fun onDetailButtonClick(myReview: MyReview) {
                removeReviewInfo = myReview
                val gson = Gson()
                val reviewSelected = gson.toJson(removeReviewInfo)
                val intent = Intent(requireContext(), CafeDetailsActivity::class.java)
                intent.putExtra(KEY_CAFE_ID, myReview.cafeId)
                intent.putExtra(EDIT_REVIEW, reviewSelected)
                startActivity(intent)
            }
        })

        myReviewAdapter.setOnImageClickListener(object :
            MyReviewAdapter.OnImageClickListener {
            override fun onImageClick(myReview: MyReview) {
                myReview.imgs?.let {
                    MyReviewImageDialog(it as ArrayList<String>).show(
                        childFragmentManager, "SampleDialog"
                    )
                }
            }
        })
    }

    private fun showEditReviewDialog() {
        val reviewEditList = ArrayList<CapinDialogButton>()
        val dialog: CapinDialog = CapinDialogBuilder("리뷰 편집")
            .setButtonArray(reviewEditList)
            .setExitButton(true)
            .build()

        reviewEditList.apply {
            add(
                CapinDialogButton("리뷰 수정",
                    ContextCompat.getColor(
                        this@MyPageReviewFragment.requireContext(),
                        R.color.maincolor_1
                    ), this@MyPageReviewFragment.requireContext(),
                    object : CapinDialogButton.OnClickListener {
                        override fun onClick() {
                            goToModifyReview()
                            dialog.dismiss()
                        }
                    })
            )
            add(
                CapinDialogButton("리뷰 삭제",
                    ContextCompat.getColor(
                        this@MyPageReviewFragment.requireContext(),
                        R.color.pointcolor_red
                    ), this@MyPageReviewFragment.requireContext(),
                    object : CapinDialogButton.OnClickListener {
                        override fun onClick() {
                            showDeleteReviewConfirmDialog()
                            dialog.dismiss()
                        }
                    })
            )
        }

        dialog.show(childFragmentManager, "picture")
    }

    private fun goToModifyReview() {
        val intent = Intent(requireContext(), WriteReviewActivity::class.java)
        val gson = Gson()
        val reviewSelected = gson.toJson(removeReviewInfo)
        intent.putExtra(EDIT_REVIEW, reviewSelected)
        startActivity(intent)
    }

    private fun showDeleteReviewConfirmDialog() {
        val dialog: CapinDialog = CapinDialogBuilder(null)
            .setContentDialogTitile("리뷰를 삭제하시겠습니까?")
            .setContentDialogButtons(true, object : DialogClickListener {
                override fun onClick() {
                    deleteMyReviewAtServer()
                    myReviewAdapter.myReviewList.remove(removeReviewInfo)
                    myReviewAdapter.notifyDataSetChanged()
                    binding.mypageReviewNumTv.text = "총 ${myReviewAdapter.myReviewList.size}개의 리뷰"
                }
            }).build()
        dialog.show(childFragmentManager, "DeleteReview")
    }

    private fun getMyReviewFromServer() {
        val capinApiService = ServiceCreator.capinApiService.getMyReview(
            userPreferenceManager.getUserAccessToken()
        )

        capinApiService.enqueue(object : Callback<ResponseMyReviewData> {
            override fun onFailure(
                call: Call<ResponseMyReviewData>,
                t: Throwable
            ) { //통신실패
                Log.d("fail", "error:$t")
            }

            override fun onResponse(
                call: Call<ResponseMyReviewData>,
                response: Response<ResponseMyReviewData>
            ) { //통신 성공
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        myReviewAdapter.myReviewList = response.body()?.reviews as MutableList<MyReview>
                        myReviewAdapter.notifyDataSetChanged()
                    }
                    binding.mypageReviewNumTv.setText("총 ${myReviewAdapter.myReviewList.size}개의 리뷰")
                    if (myReviewAdapter.myReviewList.size < 1) {
                        binding.ifBasicReviewTv.isVisible = true
                    }
                }
            }
        })
    }

    private fun deleteMyReviewAtServer() {
        val capinApiService = ServiceCreator.capinApiService.deleteMyReview(
            userPreferenceManager.getUserAccessToken(),
            reviewId = removeReviewInfo._id
        )

        capinApiService.enqueue(object : Callback<BaseResponse> {
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d("fail", "error:$t")
            }

            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful) {
                    myReviewAdapter.notifyDataSetChanged()

                    if (myReviewAdapter.myReviewList.size < 1) {
                        binding.ifBasicReviewTv.isVisible = true
                    }
                }
            }
        })

    }

    override fun onResume() {
        super.onResume()
        getMyReviewFromServer()
    }
}