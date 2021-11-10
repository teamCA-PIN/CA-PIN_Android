package com.caffeine.capin.mypage.api.request

import java.io.File

data class RequestProfileEditData(
    val nickname: String,
    val profileImg: File
)
