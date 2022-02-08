package com.caffeine.capin.data.mapper

import com.caffeine.capin.domain.entity.category.CategoryType
import com.caffeine.capin.data.dto.cafedetail.CafeLocationDTO
import com.caffeine.capin.domain.entity.map.CafeInformationEntity

class CafeListMapper: DTOMapper<CafeLocationDTO, CafeInformationEntity> {
    override fun map(from: CafeLocationDTO): CafeInformationEntity {
        return CafeInformationEntity(
            CategoryType.CATEGORY7.hexCode,
            from.id,
            from.latitude,
            from.longitude
        )
    }
}