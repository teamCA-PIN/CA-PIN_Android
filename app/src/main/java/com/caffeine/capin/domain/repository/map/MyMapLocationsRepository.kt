package com.caffeine.capin.domain.repository.map

import com.caffeine.capin.domain.entity.map.CafeInformationEntity
import io.reactivex.rxjava3.core.Single

interface MyMapLocationsRepository {
    fun getPinCafes(
        tags: List<Int?>
    ): Single<List<CafeInformationEntity>>
}