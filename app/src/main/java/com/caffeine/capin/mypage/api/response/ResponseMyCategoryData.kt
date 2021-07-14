package com.caffeine.capin.mypage.api.response

import com.caffeine.capin.mypage.mycategory.MyCategory

data class ResponseMyCategoryData(
    val message: String,
    val myCategoryList: List<MyCategory>
)