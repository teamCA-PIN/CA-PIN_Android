package com.caffeine.capin.map.dto

import com.caffeine.capin.map.dto.CafeDetailDTO

data class ResponseCafeDetail(
    val message: String,
    val cafeDetail: CafeDetailDTO,
    val isSaved: Boolean,
    val average: Float
)