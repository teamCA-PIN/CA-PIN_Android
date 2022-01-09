package com.caffeine.capin.data.dto.cafedetail

data class CafeDetailDTO(
    val tags: List<CafeTagDTO>,
    val _id: String,
    val name: String,
    val address: String,
    val instagram: String?,
    val opentime: String?,
    val opentimeHoliday: String?,
    val closetime: String?,
    val closetimeHoliday: String?,
    val offday: List<String>?,
    val latitude: Double,
    val longitude: Double,
    val img: String?,
    val rating: Float?
)
