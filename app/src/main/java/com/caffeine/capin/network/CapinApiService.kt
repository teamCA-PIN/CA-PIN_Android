package com.caffeine.capin.network

import com.caffeine.capin.cafeti.RequestCafetiData
import com.caffeine.capin.cafeti.ResponseCafetiData
import com.caffeine.capin.category.RequestArchiveCafeDTO
import com.caffeine.capin.login.*
import com.caffeine.capin.map.dto.ResponseCafeDetail
import com.caffeine.capin.map.dto.ResponseCafeList
import com.caffeine.capin.map.dto.ResponseMyMapLocations
import com.caffeine.capin.mypage.api.request.RequestDeletePinData
import com.caffeine.capin.mypage.api.request.RequestNewCategoryData
import com.caffeine.capin.mypage.api.response.ResponseMyCategoryData
import com.caffeine.capin.mypage.api.response.ResponseMyData
import com.caffeine.capin.mypage.api.response.ResponseMyPinData
import com.caffeine.capin.mypage.api.response.ResponseMyReviewData
import com.caffeine.capin.network.response.CafeMenusResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface CapinApiService {
    @GET("/cafes")
    fun getCafeLocationList(
        @Query("tags") tags: List<Int?>
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
    ): Call<ResponseMyReviewData>

    @GET("/user/categoryList")
    fun getMyCategory(
        @Header("token") token: String
    ): Call<ResponseMyCategoryData>

    @POST("/category")
    fun postNewCategory(
        @Header("token") token: String,
        @Body body: RequestNewCategoryData
    ): Call<BaseResponse>

    @DELETE("/category/{categoryId}")
    fun deleteMyCategory(
        @Header("token") token: String,
        @Path("categoryId") categoryId: String
    ): Call<BaseResponse>

    @PUT("/category/{categoryId}/")
    fun putMyCategory(
        @Header("token") token: String,
        @Path("categoryId") categoryId: String,
        @Body body: RequestNewCategoryData,
    ): Call<BaseResponse>

    //Write Review
    @Multipart
    @POST("/reviews")
    fun postReview(
        @Query("cafe") cafeId: String,
        @Part review: MultipartBody.Part,
        @Part imgs: List<MultipartBody.Part?>
    ): Completable

    @GET("/category/{categoryId}/cafes")
    fun getMyPin(
        @Header("token") token: String,
        @Path("categoryId") categoryId: String
    ): Call<ResponseMyPinData>

    @HTTP(method = "DELETE", path = "/category/{categoryId}/archive", hasBody = true)
    fun deleteMyPin(
        @Header("token") token: String,
        @Path("categoryId") categoryId: String,
        @Body body: RequestDeletePinData,
    ): Call<BaseResponse>

    @GET("/user/myInfo")
    fun getMyInfo(
        @Header("token") token: String,
    ): Call<ResponseMyData>

    @GET("/cafes/myMap")
    fun getMyMapPins(): Single<ResponseMyMapLocations>

    @GET("/user/categoryList")
    fun getCategoryList(): Single<ResponseMyCategoryData>

    @POST("/category/{cafeId}/archive")
    fun archiveCafeToCategory(
        @Path("cafeId") cafeId: String,
        @Body categoryId: RequestArchiveCafeDTO?
    ): Single<BaseResponse>

    @POST("/user/login")
    fun postLogin(
        @Body body: RequestLoginData
    ): Call<ResponseLoginData>

    @POST("/user/signup")
    fun postSignUp(
        @Body body: RequestSignUpData
    ): Call<ResponseSignUpData>

    @POST("/cafeti")
    fun postCafeti(
        @Header("token") token: String,
        @Body body: RequestCafetiData
    ): Call<ResponseCafetiData>

    @POST("/user/emailAuth")
    fun postLoginPw(
        @Body body: RequestLoginPwData
    ): Call<ResponseLoginPwData>

    @PUT("/user/changePassword")
    fun postFindPw(
        @Body body: RequestFindPwData
    ): Call<ResponseFindPwData>
}
