package com.caffeine.capin.domain.entity.profile

data class ProfileEntity(
    val profileImage: String,
    val nickName: String,
    val email: String,
    val cafetiType: String,
    val pinNum: Int,
    val reviewNum: Int
)