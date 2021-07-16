package com.caffeine.capin.profile.model

data class ProfileEntity(
    val profileImage: String,
    val nickName: String,
    val email: String,
    val cafetiType: String,
    val pinNum: Int,
    val reviewNum: Int
)