package com.caffeine.capin.mypage.pin

data class PinInfo(
    val name: String,
    val address: String,
    val tags: List<PinInfoTag>
)
