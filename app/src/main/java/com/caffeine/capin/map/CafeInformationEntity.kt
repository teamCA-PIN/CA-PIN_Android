package com.caffeine.capin.map

data class CafeInformationEntity(
    val name: String,
    val latitude: Double,
    val longtitude: Double,
    val tag: String,
    val address: String,
    val cafeImage: String,
    val rating: Double
)