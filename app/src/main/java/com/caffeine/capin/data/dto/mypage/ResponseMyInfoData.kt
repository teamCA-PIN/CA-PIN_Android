package com.caffeine.capin.data.dto.mypage

data class ResponseMyInfoData(
    val cafeti: ResponseMyCafetiData,
    val nickname: String,
    val email: String,
    val profileImg: String,
    val reviewNum: Int,
    val pinNum: Int
)
