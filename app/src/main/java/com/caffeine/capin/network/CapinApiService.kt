package com.caffeine.capin.network

import com.caffeine.capin.map.dto.ResponseCafeDetail
import com.caffeine.capin.map.dto.ResponseCafeList
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface CapinApiService {
    @GET("/cafes")
    fun getCafeLocationList(
        @QueryMap tags: Map<String, Int?>
    ): Single<ResponseCafeList>

    @GET("/cafes/detail/{cafeId}")
    fun getCafeDetail(@Path("cafeId") cafeId: String): Single<ResponseCafeDetail>
}