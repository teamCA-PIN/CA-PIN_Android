package com.caffeine.capin.map.repository

import com.caffeine.capin.map.entity.CafeDetailEntity
import com.caffeine.capin.map.entity.CafeInformationEntity
import io.reactivex.rxjava3.core.Single

interface CafeListRepository {
    fun getCafeList(
        tag1: Int?,
        tag2: Int?,
        tag3: Int?,
        tag4: Int?,
        tag5: Int?,
        tag6: Int?
    ): Single<List<CafeInformationEntity>>

    fun getCafeDetail(cafeId: String): Single<CafeDetailEntity>
}