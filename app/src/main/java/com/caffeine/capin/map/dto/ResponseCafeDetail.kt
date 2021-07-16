package com.caffeine.capin.map.dto

import com.caffeine.capin.detail.CafeDetails

data class ResponseCafeDetail(
    val message: String,
    val cafeDetail: CafeDetailDTO
) {
    //Fixme: 서버가 수정되어 모델을 바꾸었습니다. startRate가 Flaot형태로, img를 nullable하게 바꾸었습니다.
    //Fixme: isSaved, average는 서버에서 이제 안넘어와서 삭제하였습니다! 혁이형 참고하시고 맞게 수정해서 사용부탁드려요~
    fun toCafeDetails() = CafeDetails(
        id = cafeDetail._id,
        cafeName = cafeDetail.name,
        mainImageUrl = cafeDetail.img,
        starRate = cafeDetail.rating ?: 0.0f,
        address = cafeDetail.address,
        tags = cafeDetail.tags.map { it.name },
        instagramId = cafeDetail.instagram.orEmpty(),
        operationTime = "평일 %s~%s %s\n공휴일 %s~%s".format(
            cafeDetail.opentime,
            cafeDetail.closetime,
            offDays(),
            cafeDetail.opentimeHoliday,
            cafeDetail.closetimeHoliday
        ),
        reviews = listOf(),
    )

    private fun offDays(): String {
        val offDays = cafeDetail.offday?.joinToString(", ") ?: return ""
        return "($offDays 휴무)"
    }
}
