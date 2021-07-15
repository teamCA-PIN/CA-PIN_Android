package com.caffeine.capin.map.datasource

import com.caffeine.capin.map.dto.ResponseMyMapLocations
import com.caffeine.capin.network.CapinApiService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MyMapLocationsDataSourceImpl @Inject constructor(
    private val capinApiService: CapinApiService
): MyMapLocationsDataSource {
    override fun getPinCafes(): Single<ResponseMyMapLocations> =
        capinApiService.getMyMapPins()
}