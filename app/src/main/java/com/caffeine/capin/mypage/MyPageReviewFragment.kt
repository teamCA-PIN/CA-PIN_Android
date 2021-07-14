package com.caffeine.capin.mypage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.caffeine.capin.R
import com.caffeine.capin.customview.*
import com.caffeine.capin.databinding.FragmentMyPageReviewBinding
import com.caffeine.capin.mypage.api.ResponseMyReviewData
import com.caffeine.capin.mypage.myreview.MyReview
import com.caffeine.capin.mypage.myreview.MyReviewAdapter
import com.caffeine.capin.network.ServiceCreator
import com.caffeine.capin.preference.UserPreferenceManager
import com.caffeine.capin.util.AutoClearedValue
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MyPageReviewFragment : Fragment() {

    private var binding by AutoClearedValue<FragmentMyPageReviewBinding>()
    private lateinit var myReviewAdapter: MyReviewAdapter
    private lateinit var removeReviewInfo: MyReview

    @Inject lateinit var userPreferenceManager: UserPreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageReviewBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myReviewAdapter = MyReviewAdapter()
        binding.mypageReviewRcv.adapter = myReviewAdapter

        myReviewAdapter.setOnEditButtonClickListener(object :
            MyReviewAdapter.OnEditButtonClickListener {

            override fun onEditButtonClick(myReview: MyReview) {
                removeReviewInfo = myReview
                showEditReviewDialog()
            }
        })

        getMyReviewFromServer()
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
                    ContextCompat.getColor(this@MyPageReviewFragment.requireContext(), R.color.maincolor_1), this@MyPageReviewFragment.requireContext(),
                    object : CapinDialogButton.OnClickListener {
                        override fun onClick() {

                            dialog.dismiss()
                        }
                    })
            )
            add(
                CapinDialogButton("리뷰 삭제",
                    ContextCompat.getColor(this@MyPageReviewFragment.requireContext(), R.color.pointcolor_red), this@MyPageReviewFragment.requireContext(),
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

    private fun showDeleteReviewConfirmDialog() {
        val dialog: CapinDialog = CapinDialogBuilder(null)
            .setContentDialogTitile("리뷰를 삭제하시겠습니까?")
            .setContentDialogButtons(true, object: DialogClickListener {
                override fun onClick() {
                    myReviewAdapter.myReviewList.remove(removeReviewInfo)
                    myReviewAdapter.notifyDataSetChanged()
                    binding.mypageReviewNumTv.setText("총 ${myReviewAdapter.myReviewList.size}개의 뷰")
                }
            }).build()
        dialog.show(childFragmentManager, "DeleteReview")
    }

    private fun getMyReviewFromServer() {
        userPreferenceManager.setUserToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI2MGVlODMzNjhiNzg1MTFhMDc5MGRjNzkiLCJpYXQiOjE2MjYyNDM5NjEsImV4cCI6MTYyNjMzMDM2MX0.govjwTEd5FiH4kZ01J_qe44v21lhgj7pKtKQ2MNbaxk")
        val capinApiService = ServiceCreator.capinApiService.getMyReview(
            userPreferenceManager.getUserToken()
        )

        capinApiService.enqueue(object : Callback<ResponseMyReviewData>{
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
                    myReviewAdapter.myReviewList = response.body()?.reviews as MutableList<MyReview>
                    Log.d("리미", response.body().toString())
                    myReviewAdapter.notifyDataSetChanged()
                }
                binding.mypageReviewNumTv.setText("총 ${myReviewAdapter.myReviewList.size}개의 뷰")

                if(myReviewAdapter.myReviewList.size > 1) {
                    binding.ifBasicReviewTv.isVisible = false
                }
            }
        })
    }
}