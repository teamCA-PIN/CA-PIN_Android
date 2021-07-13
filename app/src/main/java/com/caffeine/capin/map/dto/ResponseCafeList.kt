package com.caffeine.capin.map.dto

import com.caffeine.capin.map.dto.CafeLocationDTO

data class ResponseCafeList(
    val message: String,
    val cafeLocations: List<CafeLocationDTO>
)
