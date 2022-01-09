package com.caffeine.capin.data.mapper

import com.caffeine.capin.data.dto.cafedetail.MyMapLocationDTO
import com.caffeine.capin.domain.entity.map.CafeInformationEntity

class MyMapPinMapper : DTOMapper<List<MyMapLocationDTO>?, List<CafeInformationEntity>> {
    override fun map(from: List<MyMapLocationDTO>?): List<CafeInformationEntity> {
        val myMapList = mutableListOf<CafeInformationEntity>()
        from?.forEach { categories ->
            categories.cafes.forEach { cafes ->
                myMapList.add(
                    CafeInformationEntity(
                        categories.color,
                        cafes.id,
                        cafes.latitude,
                        cafes.longitude
                    )
                )
            }
        }
        return myMapList
    }
}