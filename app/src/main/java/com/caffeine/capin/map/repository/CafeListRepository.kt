package com.caffeine.capin.map.repository

import com.caffeine.capin.map.entity.CafeDetailEntity
import com.caffeine.capin.map.entity.CafeInformationEntity
import io.reactivex.rxjava3.core.Single

interface CafeListRepository {
    fun getCafeList(tags: List<Int?>): Single<List<CafeInformationEntity>>
    fun getCafeDetail(cafeId: String): Single<CafeDetailEntity>
}