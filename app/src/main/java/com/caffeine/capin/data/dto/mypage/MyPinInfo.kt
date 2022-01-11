package com.caffeine.capin.data.dto.mypage

data class MyPinInfo(
    val _id: String,
    val address: String,
    val name: String,
    val tags: List<PinInfoTag>
)
