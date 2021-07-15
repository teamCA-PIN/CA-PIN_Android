package com.caffeine.capin.map.datasource

import com.caffeine.capin.map.dto.ResponseMyMapLocations
import io.reactivex.rxjava3.core.Single

interface MyMapLocationsDataSource {
    fun getPinCafes(): Single<ResponseMyMapLocations>
}