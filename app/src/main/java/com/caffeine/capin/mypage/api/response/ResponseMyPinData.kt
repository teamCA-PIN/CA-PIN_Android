package com.caffeine.capin.mypage.api.response

import com.caffeine.capin.mypage.pin.MyPinInfo

data class ResponseMyPinData(
    val cafeDetail: List<MyPinInfo>,
    val message: String
)
