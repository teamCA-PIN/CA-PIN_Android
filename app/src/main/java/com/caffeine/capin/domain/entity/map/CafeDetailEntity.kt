package com.caffeine.capin.domain.entity.map

import com.caffeine.capin.data.dto.cafedetail.CafeTagDTO

data class CafeDetailEntity(
    val tags: List<CafeTagDTO>?,
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
    val average: Float?,
    val img: String?,
    val isSaved: Boolean
)