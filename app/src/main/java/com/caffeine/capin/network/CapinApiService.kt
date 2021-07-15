package com.caffeine.capin.network

import com.caffeine.capin.map.dto.ResponseCafeDetail
import com.caffeine.capin.map.dto.ResponseCafeList
import com.caffeine.capin.mypage.api.request.RequestDeletePinData
import com.caffeine.capin.mypage.api.request.RequestNewCategoryData
import com.caffeine.capin.mypage.api.response.ResponseMyCategoryData
import com.caffeine.capin.mypage.api.response.ResponseMyPinData
import com.caffeine.capin.mypage.api.response.ResponseMyReviewData
import com.caffeine.capin.network.response.CafeMenusResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

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

    @GET("/cafes/{cafeId}/menus")
    fun getCafeMenus(
        @Path("cafeId") cafeId: String
    ): Call<CafeMenusResponse>


    //mypage
    @GET("/user/reviews")
    fun getMyReview(
        @Header("token") token: String
    ) : Call<ResponseMyReviewData>

    @GET("/user/categoryList")
    fun getMyCategory(
        @Header("token") token: String
    ) : Call<ResponseMyCategoryData>

    @POST("/category")
    fun postNewCategory(
        @Header("token") token: String,
        @Body body: RequestNewCategoryData
    ) : Call<BaseResponse>

    @DELETE("/category/{categoryId}")
    fun deleteMyCategory(
        @Header("token") token: String,
        @Path("categoryId") categoryId: String
    ) : Call<BaseResponse>

    @PUT("/category/{categoryId}/")
    fun putMyCategory(
        @Header("token") token: String,
        @Path("categoryId") categoryId: String,
        @Body body: RequestNewCategoryData,
    ) : Call<BaseResponse>

    @GET("/category/{categoryId}/cafes")
    fun getMyPin(
        @Header("token") token: String,
        @Path("categoryId") categoryId: String
    ) : Call<ResponseMyPinData>

    @HTTP(method = "DELETE", path = "/category/{categoryId}/archive", hasBody = true)
    fun deleteMyPin(
        @Header("token") token: String,
        @Path("categoryId") categoryId: String,
        @Body body: RequestDeletePinData,
    ) : Call<BaseResponse>

}
