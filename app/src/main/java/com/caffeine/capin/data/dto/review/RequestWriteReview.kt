package com.caffeine.capin.data.dto.review

data class RequestWriteReview(
    val recommend: List<Int?>?,
    val content: String,
    val rating: Float,
    val isAllDeleted: Boolean?
)