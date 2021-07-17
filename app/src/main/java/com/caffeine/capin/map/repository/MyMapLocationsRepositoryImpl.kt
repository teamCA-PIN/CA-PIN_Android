package com.caffeine.capin.map.repository

import com.caffeine.capin.map.mapper.MyMapPinMapper
import com.caffeine.capin.map.entity.CafeInformationEntity
import com.caffeine.capin.network.CapinApiService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MyMapLocationsRepositoryImpl @Inject constructor(
    private val capinApiService: CapinApiService,
    private val myMapPinMapper: MyMapPinMapper
) : MyMapLocationsRepository {
    override fun getPinCafes(
        tags: List<Int?>
    ): Single<List<CafeInformationEntity>> =
        capinApiService.getMyMapPins(tags).map { response ->
            myMapPinMapper.map(response.myMapLocationDTO)
        }
}