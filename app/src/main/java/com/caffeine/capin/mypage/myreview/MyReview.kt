package com.caffeine.capin.mypage.myreview

data class MyReview(
    val _id: String,
    val cafeId: String,
    val cafeName: String,
    val content: String,
    val create_at: String,
    val imgs: List<String>,
    val rating: Double,
    val recommend: List<Int>
)