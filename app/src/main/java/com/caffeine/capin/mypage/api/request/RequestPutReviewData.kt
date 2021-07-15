package com.caffeine.capin.mypage.api.request

data class RequestPutReviewData(
    val recommend: List<Int?>?,
    val content: String,
    val rating: Float
)
