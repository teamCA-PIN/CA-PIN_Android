package com.caffeine.capin.review.write

data class RequestWriteReview(
    val recommend: List<Int?>,
    val content: String,
    val rating: Float
)