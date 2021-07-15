package com.caffeine.capin.map.datasource

import com.caffeine.capin.map.dto.ResponseCafeDetail
import com.caffeine.capin.map.dto.ResponseCafeList
import com.caffeine.capin.network.CapinApiService
import io.reactivex.rxjava3.core.Single
import retrofit2.http.QueryMap

class CafeListDataSourceImpl(private val capinApiService: CapinApiService) : CafeListDataSource {
    override fun getCafeList(tags: List<Int?>): Single<ResponseCafeList> = capinApiService.getCafeLocationList(tags)

    override fun getCafeDetail(cafeId: String): Single<ResponseCafeDetail> =
        capinApiService.getCafeDetail(cafeId)
}