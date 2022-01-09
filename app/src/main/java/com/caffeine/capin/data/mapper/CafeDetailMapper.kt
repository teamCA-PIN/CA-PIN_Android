package com.caffeine.capin.data.mapper

import com.caffeine.capin.data.dto.cafedetail.ResponseCafeDetail
import com.caffeine.capin.domain.entity.map.CafeDetailEntity

class CafeDetailMapper: DTOMapper<ResponseCafeDetail, CafeDetailEntity> {
    override fun map(from: ResponseCafeDetail): CafeDetailEntity =
        CafeDetailEntity(
            from.cafeDetail.tags,
            from.cafeDetail._id,
            from.cafeDetail.name,
            from.cafeDetail.address,
            from.cafeDetail.instagram,
            from.cafeDetail.opentime,
            from.cafeDetail.opentimeHoliday,
            from.cafeDetail.closetime,
            from.cafeDetail.closetimeHoliday,
            from.cafeDetail.offday,
            from.cafeDetail.latitude,
            from.cafeDetail.longitude,
            from.cafeDetail.rating,
            from.cafeDetail.img,
            from.isSaved
        )
}