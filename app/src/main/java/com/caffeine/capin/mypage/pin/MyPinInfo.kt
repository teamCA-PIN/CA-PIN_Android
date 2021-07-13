package com.caffeine.capin.mypage.pin

data class MyPinInfo(
    val name: String,
    val address: String,
    val tags: List<PinInfoTag>
)
