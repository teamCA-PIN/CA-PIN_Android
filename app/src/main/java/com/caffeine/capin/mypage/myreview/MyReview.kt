package com.caffeine.capin.mypage.myreview

data class MyReview(
    val cafeName: String,
    val rating: String,
    val content: String,
    val recommend: List<Int?>,
    val imgs: List<String?>
)
