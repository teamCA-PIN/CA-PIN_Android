package com.caffeine.capin.mypage.review

data class Review(
    val _id : String,
    val nickname: String,
    val date: String,
    val rating: Float,
    val recommend: Int?,
    val content: String,
    val imgs: String?
)
