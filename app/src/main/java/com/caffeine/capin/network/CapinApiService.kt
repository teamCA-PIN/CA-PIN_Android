package com.caffeine.capin.network

import com.caffeine.capin.map.dto.ResponseCafeDetail
import com.caffeine.capin.map.dto.ResponseCafeList
import com.caffeine.capin.mypage.api.request.RequestDeletePinData
import com.caffeine.capin.mypage.api.request.RequestNewCategoryData
import com.caffeine.capin.mypage.api.response.*
import com.caffeine.capin.network.response.CafeMenusResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface CapinApiService {
    @GET("/cafes")
    fun getCafeLocationList(
        @QueryMap tags: Map<String, Int?>
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

    @GET("/user/myInfo")
    fun getMyInfo(
        @Header("token") token: String,
    ) : Call<ResponseMyData>

    @DELETE("/reviews/{reviewId}")
    fun deleteMyReview(
        @Header("token") token: String,
        @Path("reviewId") reviewId: String
    ) : Call<BaseResponse>
}
