package com.caffeine.capin.map

import com.caffeine.capin.network.DTOMapper

class CafeDetailMapper:DTOMapper<ResponseCafeDetail, CafeDetailEntity> {
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
            from.isSaved,
            from.average
        )
}