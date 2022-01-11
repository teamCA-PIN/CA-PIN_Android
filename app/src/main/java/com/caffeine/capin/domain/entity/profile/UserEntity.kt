package com.caffeine.capin.domain.entity.profile

data class UserEntity(
    val nickName: String,
    val email: String,
    val pinNum: Int,
    val reviewNum: Int,
    val profileImg: String,
    val cafeti: String
)