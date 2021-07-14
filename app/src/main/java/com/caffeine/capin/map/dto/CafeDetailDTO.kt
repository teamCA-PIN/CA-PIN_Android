package com.caffeine.capin.map.dto

import com.caffeine.capin.tagfilter.CafeTagEntity

data class CafeDetailDTO(
    val tags: List<CafeTagEntity>,
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
    val longitude: Double
)