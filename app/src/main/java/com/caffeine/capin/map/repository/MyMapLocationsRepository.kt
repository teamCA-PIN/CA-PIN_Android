package com.caffeine.capin.map.repository

import com.caffeine.capin.map.dto.CafeLocationDTO
import com.caffeine.capin.map.entity.CafeInformationEntity
import io.reactivex.rxjava3.core.Single

interface MyMapLocationsRepository {
    fun getPinCafes(): Single<List<CafeInformationEntity>>
}