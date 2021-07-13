package com.caffeine.capin.map

import com.caffeine.capin.network.DTOMapper

class CafeListMapper: DTOMapper<CafeLocationDTO, CafeInformationEntity> {
    override fun map(from: CafeLocationDTO): CafeInformationEntity {
        return CafeInformationEntity(
            from.id,
            from.latitude,
            from.longitude
        )
    }
}