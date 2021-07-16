package com.caffeine.capin.mypage.api.response

data class ResponseMyInfoData(
    val cafeti: ResponseMyCafetiData,
    val nickname: String,
    val email: String,
    val profileImg: String,
    val reviewNum: Int,
    val pinNum: Int
)
