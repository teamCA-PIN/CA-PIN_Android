package com.caffeine.capin.map

import com.caffeine.capin.map.dto.MyMapLocationDTO
import com.caffeine.capin.map.entity.CafeInformationEntity
import com.caffeine.capin.network.DTOMapper

class MyMapPinMapper : DTOMapper<List<MyMapLocationDTO>, List<CafeInformationEntity>> {
    override fun map(from: List<MyMapLocationDTO>): List<CafeInformationEntity> {
        val myMapList = mutableListOf<CafeInformationEntity>()
        from.forEach { categories ->
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