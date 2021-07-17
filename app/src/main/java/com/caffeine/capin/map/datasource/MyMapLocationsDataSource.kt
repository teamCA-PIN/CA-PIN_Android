package com.caffeine.capin.map.datasource

import com.caffeine.capin.map.dto.ResponseMyMapLocations
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Query

interface MyMapLocationsDataSource {
    fun getPinCafes(
        tags: List<Int?>
    ): Single<ResponseMyMapLocations>
}