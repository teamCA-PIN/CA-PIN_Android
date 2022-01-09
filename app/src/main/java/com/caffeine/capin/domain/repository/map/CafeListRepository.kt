package com.caffeine.capin.domain.repository.map

import com.caffeine.capin.domain.entity.map.CafeDetailEntity
import com.caffeine.capin.domain.entity.map.CafeInformationEntity
import io.reactivex.rxjava3.core.Single

interface CafeListRepository {
    fun getCafeList(tags: List<Int?>): Single<List<CafeInformationEntity>>
    fun getCafeDetail(cafeId: String): Single<CafeDetailEntity>
}