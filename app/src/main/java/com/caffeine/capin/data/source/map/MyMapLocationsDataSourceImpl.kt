package com.caffeine.capin.data.source.map

import com.caffeine.capin.data.dto.map.ResponseMyMapLocations
import com.caffeine.capin.data.network.CapinApiService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MyMapLocationsDataSourceImpl @Inject constructor(
    private val capinApiService: CapinApiService
): MyMapLocationsDataSource {
    override fun getPinCafes(
        tags: List<Int?>
    ): Single<ResponseMyMapLocations> =
        capinApiService.getMyMapPins(tags)
}