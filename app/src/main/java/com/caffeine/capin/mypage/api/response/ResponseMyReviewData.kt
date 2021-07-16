package com.caffeine.capin.mypage.api.response

import com.caffeine.capin.mypage.myreview.MyReview

data class ResponseMyReviewData(
    val message: String,
    val reviews: List<MyReview>
)