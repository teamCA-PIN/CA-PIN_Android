package com.caffeine.capin.network

import com.caffeine.capin.map.dto.ResponseCafeDetail
import com.caffeine.capin.map.dto.ResponseCafeList
import com.caffeine.capin.mypage.api.ResponseMyReviewData
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface CapinApiService {
    @GET("/cafes")
    fun getCafeLocationList(
        @Query("tags") tag1: Int?,
        @Query("tags") tag2: Int?,
        @Query("tags") tag3: Int?,
        @Query("tags") tags4: Int?,
        @Query("tags") tags5: Int?,
        @Query("tags") tags6: Int?
    ): Single<ResponseCafeList>

    @GET("/cafes/detail/{cafeId}")
    fun getCafeDetail(@Path("cafeId") cafeId: String): Single<ResponseCafeDetail>

    @GET("/user/reviews")
    fun getMyReview(
        @Header("token") token: String
    ) : Call<ResponseMyReviewData>
}