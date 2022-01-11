package com.caffeine.capin.data.dto.mypage

data class RequestPutReviewData(
    val recommend: List<Int?>?,
    val content: String,
    val rating: Float
)
