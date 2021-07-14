package com.caffeine.capin.mypage.mycategory

data class MyCategory(
    val _id: String,
    val cafes: List<String>,
    var color: String,
    var name: String
)