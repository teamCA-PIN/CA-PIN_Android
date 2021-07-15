package com.caffeine.capin.mypage.api.response

data class ResponseMyInfoData(
    val cafeti: ResponseMyCafetiData,
    val email: String,
    val nickname: String,
    val pinNum: Int,
    val profileImg: String,
    val reviewNum: Int
)
