package com.caffeine.capin.map

import com.caffeine.capin.category.CategoryType
import com.caffeine.capin.map.dto.CafeLocationDTO
import com.caffeine.capin.map.entity.CafeInformationEntity
import com.caffeine.capin.network.DTOMapper

class CafeListMapper: DTOMapper<CafeLocationDTO, CafeInformationEntity> {
    override fun map(from: CafeLocationDTO): CafeInformationEntity {
        return CafeInformationEntity(
            CategoryType.CATEGORY10.hexCode,
            from.id,
            from.latitude,
            from.longitude
        )
    }
}