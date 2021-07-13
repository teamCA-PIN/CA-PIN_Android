package com.caffeine.capin.map

data class ResponseCafeDetail(
    val message: String,
    val cafeDetail: CafeDetailDTO,
    val isSaved: Boolean,
    val average: Float
)