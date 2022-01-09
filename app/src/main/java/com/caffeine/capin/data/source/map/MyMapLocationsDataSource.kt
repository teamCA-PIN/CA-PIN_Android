package com.caffeine.capin.data.source.map

import com.caffeine.capin.data.dto.map.ResponseMyMapLocations
import io.reactivex.rxjava3.core.Single

interface MyMapLocationsDataSource {
    fun getPinCafes(
        tags: List<Int?>
    ): Single<ResponseMyMapLocations>
}