package com.caffeine.capin.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.caffeine.capin.R
import com.caffeine.capin.databinding.FragmentMyPageCategoryBinding
import com.caffeine.capin.databinding.FragmentMyPageReviewBinding
import com.caffeine.capin.mypage.myreview.MyReview
import com.caffeine.capin.mypage.myreview.MyReviewAdapter
import com.caffeine.capin.util.AutoClearedValue


class MyPageReviewFragment : Fragment() {

    private var binding by AutoClearedValue<FragmentMyPageReviewBinding>()

    private lateinit var myReviewAdapter: MyReviewAdapter

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

        myReviewAdapter.myReviewList.addAll(
            listOf<MyReview>(
                MyReview(
                    cafeName = "후엘고",
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
                    cafeName = "후엘고후엘고",
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
                    cafeName = "꺄항냐항",
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
                    cafeName = "후엘고",
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
                    cafeName = "후엘고",
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
                    cafeName = "후엘고",
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
    }
}