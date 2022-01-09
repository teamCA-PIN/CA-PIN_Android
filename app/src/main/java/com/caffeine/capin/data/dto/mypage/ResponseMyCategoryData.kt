package com.caffeine.capin.data.dto.mypage

import com.caffeine.capin.data.dto.category.MyCategory

data class ResponseMyCategoryData(
    val message: String,
    val myCategoryList: List<MyCategory>
)