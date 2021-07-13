package com.caffeine.capin.network

import com.caffeine.capin.map.RequestTag
import com.caffeine.capin.map.ResponseCafeDetail
import com.caffeine.capin.map.ResponseCafeList
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CapinApiService {
    @GET("/cafes")
    fun getCafeLocationList(@Query("tags") tags: List<RequestTag>? ): Single<ResponseCafeList>

    @GET("/cafes/detail/{cafeId}")
    fun getCafeDetail(@Path ("cafeId") cafeId: String): Single<ResponseCafeDetail>
}