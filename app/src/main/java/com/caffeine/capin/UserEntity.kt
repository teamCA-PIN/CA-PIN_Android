package com.caffeine.capin

data class UserEntity(
    val nickName: String,
    val email: String,
    val pinNum: Int,
    val reviewNum: Int,
    val profileImg: String,
    val cafeti: CafetiEntity
)