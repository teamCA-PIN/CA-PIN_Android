package com.caffeine.capin.map.repository

import com.caffeine.capin.map.dto.CafeLocationDTO
import com.caffeine.capin.map.entity.CafeInformationEntity
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Query

interface MyMapLocationsRepository {
    fun getPinCafes(
        tags: List<Int?>
    ): Single<List<CafeInformationEntity>>
}