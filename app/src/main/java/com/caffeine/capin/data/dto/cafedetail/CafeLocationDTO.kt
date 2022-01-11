package com.caffeine.capin.data.dto.cafedetail

import com.google.gson.annotations.SerializedName

data class CafeLocationDTO(
    @SerializedName("_id")
    val id: String,
    val latitude: Double,
    val longitude: Double
)
