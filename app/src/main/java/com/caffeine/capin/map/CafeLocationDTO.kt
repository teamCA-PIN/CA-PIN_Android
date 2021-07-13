package com.caffeine.capin.map

import com.google.gson.annotations.SerializedName

data class CafeLocationDTO(
    @SerializedName("_id")
    val id: String,
    val latitude: Double,
    val longitude: Double
)