package com.caffeine.capin.domain.repository.map

import com.caffeine.capin.data.mapper.MyMapPinMapper
import com.caffeine.capin.domain.entity.map.CafeInformationEntity
import com.caffeine.capin.data.network.CapinApiService
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