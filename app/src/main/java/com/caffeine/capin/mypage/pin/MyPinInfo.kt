package com.caffeine.capin.mypage.pin

data class MyPinInfo(
    val _id: String,
    val address: String,
    val name: String,
    val tags: List<PinInfoTag>
)
