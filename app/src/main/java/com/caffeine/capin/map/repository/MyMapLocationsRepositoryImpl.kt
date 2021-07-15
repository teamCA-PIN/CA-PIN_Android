package com.caffeine.capin.map.repository

import com.caffeine.capin.map.MyMapPinMapper
import com.caffeine.capin.map.entity.CafeInformationEntity
import com.caffeine.capin.network.CapinApiService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MyMapLocationsRepositoryImpl @Inject constructor(
    private val capinApiService: CapinApiService,
    private val myMapPinMapper: MyMapPinMapper
) : MyMapLocationsRepository {
    override fun getPinCafes(): Single<List<CafeInformationEntity>> =
        capinApiService.getMyMapPins().map { response ->
            myMapPinMapper.map(response.myMapLocationDTO)
        }
}