package com.caffeine.capin.review.write

import com.google.gson.annotations.SerializedName

data class RequestWriteReview(
    val recommend: List<Int?>?,
    val content: String,
    val rating: Float,
    val isAllDeleted: Boolean?
)