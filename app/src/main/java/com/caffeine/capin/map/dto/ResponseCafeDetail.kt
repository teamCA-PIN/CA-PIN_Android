package com.caffeine.capin.map.dto

import com.caffeine.capin.detail.CafeDetails

data class ResponseCafeDetail(
    val message: String,
    val cafeDetail: CafeDetailDTO
) {
    fun toCafeDetails() = CafeDetails(
        id = cafeDetail._id,
        cafeName = cafeDetail.name,
        mainImageUrl = cafeDetail.img,
        starRate = cafeDetail.rating?.toDouble() ?: 0.0,
        address = cafeDetail.address,
        tags = cafeDetail.tags.map { it.name },
        instagramId = cafeDetail.instagram.orEmpty(),
        operationTime = "평일 %s~%s %s\n공휴일 %s~%s".format(
            cafeDetail.opentime,
            cafeDetail.closetime,
            offDays(),
            cafeDetail.opentimeHoliday,
            cafeDetail.closetimeHoliday,
        ),
    )

    private fun offDays(): String {
        val offDays = cafeDetail.offday?.joinToString(", ") ?: return ""
        return "($offDays 휴무)"
    }
}
