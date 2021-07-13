package com.caffeine.capin.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.caffeine.capin.R
import com.caffeine.capin.customview.*
import com.caffeine.capin.databinding.FragmentMyPageCategoryBinding
import com.caffeine.capin.databinding.FragmentMyPageReviewBinding
import com.caffeine.capin.mypage.myreview.MyReview
import com.caffeine.capin.mypage.myreview.MyReviewAdapter
import com.caffeine.capin.mypage.pin.MyPinInfo
import com.caffeine.capin.util.AutoClearedValue


class MyPageReviewFragment : Fragment() {

    private var binding by AutoClearedValue<FragmentMyPageReviewBinding>()

    private lateinit var myReviewAdapter: MyReviewAdapter

    private lateinit var removeReviewInfo: MyReview

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

        myReviewAdapter.myReviewList.addAll(
            listOf<MyReview>(
                MyReview(
                    cafeName = "후엘고1",
                    rating = "3.5",
                    content = "무엇보다 커피가 정말 맛있고, 디저트로 준비돼 있던 쿠키와 휘낭시에도 맛있었습니다.  브라운크림은 꼭 드세요 ! 무엇보다 커피가 정말 맛있고, 디저트로 준비돼 있던 쿠키와 휘낭시에도 맛있었습니다.  브라운크림은 꼭 드세요 !무엇보다 커피가 정말 맛있고, 디저트로 준비돼",
                    recommend = listOf(
                        0,
                        1
                    ),
                    imgs = listOf(
                        ""
                    )
                ),
                MyReview(
                    cafeName = "후엘고후엘고2",
                    rating = "4.0",
                    content = "무엇보다 커피가 정말 맛있고, 디저트로 준비돼 있던 쿠키와 휘낭시에도 맛있었습니다.  브라운크림은 꼭 드세요 ! 무엇보다 커피가 정말 맛있고, 디저트로 준비돼 있던 쿠키와 휘낭시에도 맛있었습니다.  브라운크림은 꼭 드세요 !무엇보다 커피가 정말 맛있고, 디저트로 준비돼",
                    recommend = listOf(
                        0,
                        1
                    ),
                    imgs = listOf(
                        ""
                    )
                ),
                MyReview(
                    cafeName = "꺄항냐항3",
                    rating = "3.5",
                    content = "무엇보다 커피가 정말 맛있고, 디저트로 준비돼 있던 쿠키와 휘낭시에도 맛있었습니다.  브라운크림은 꼭 드세요 ! 무엇보다 커피가 정말 맛있고, 디저트로 준비돼 있던 쿠키와 휘낭시에도 맛있었습니다.  브라운크림은 꼭 드세요 !무엇보다 커피가 정말 맛있고, 디저트로 준비돼",
                    recommend = listOf(
                        0,
                        1
                    ),
                    imgs = listOf(
                        ""
                    )
                ),
                MyReview(
                    cafeName = "후엘고4",
                    rating = "3.5",
                    content = "무엇보다 커피가 정말 맛있고, 디저트로 준비돼 있던 쿠키와 휘낭시에도 맛있었습니다.  브라운크림은 꼭 드세요 ! 무엇보다 커피가 정말 맛있고, 디저트로 준비돼 있던 쿠키와 휘낭시에도 맛있었습니다.  브라운크림은 꼭 드세요 !무엇보다 커피가 정말 맛있고, 디저트로 준비돼",
                    recommend = listOf(
                        0,
                        1
                    ),
                    imgs = listOf(
                        ""
                    )
                ),
                MyReview(
                    cafeName = "후엘고5",
                    rating = "3.5",
                    content = "무엇보다 커피가 정말 맛있고, 디저트로 준비돼 있던 쿠키와 휘낭시에도 맛있었습니다.  브라운크림은 꼭 드세요 ! 무엇보다 커피가 정말 맛있고, 디저트로 준비돼 있던 쿠키와 휘낭시에도 맛있었습니다.  브라운크림은 꼭 드세요 !무엇보다 커피가 정말 맛있고, 디저트로 준비돼",
                    recommend = listOf(
                        0,
                        1
                    ),
                    imgs = listOf(
                        ""
                    )
                ),
                MyReview(
                    cafeName = "후엘고6",
                    rating = "3.5",
                    content = "무엇보다 커피가 정말 맛있고, 디저트로 준비돼 있던 쿠키와 휘낭시에도 맛있었습니다.  브라운크림은 꼭 드세요 ! 무엇보다 커피가 정말 맛있고, 디저트로 준비돼 있던 쿠키와 휘낭시에도 맛있었습니다.  브라운크림은 꼭 드세요 !무엇보다 커피가 정말 맛있고, 디저트로 준비돼",
                    recommend = listOf(
                        0,
                        1
                    ),
                    imgs = listOf(
                        ""
                    )
                )
            )
        )

        myReviewAdapter.notifyDataSetChanged()
        binding.mypageReviewNumTv.setText("총 ${myReviewAdapter.myReviewList.size}개의 뷰")
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


}