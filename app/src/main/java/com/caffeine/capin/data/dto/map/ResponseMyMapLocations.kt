package com.caffeine.capin.data.dto.map

import com.caffeine.capin.data.dto.cafedetail.MyMapLocationDTO
import com.google.gson.annotations.SerializedName

data class ResponseMyMapLocations(
    val message: String,
    @SerializedName("myMapLocations")
    val myMapLocationDTO: List<MyMapLocationDTO>?
)