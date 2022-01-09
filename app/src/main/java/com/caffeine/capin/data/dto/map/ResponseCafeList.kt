package com.caffeine.capin.data.dto.map

import com.caffeine.capin.data.dto.cafedetail.CafeLocationDTO

data class ResponseCafeList(
    val message: String,
    val cafeLocations: List<CafeLocationDTO>
)
