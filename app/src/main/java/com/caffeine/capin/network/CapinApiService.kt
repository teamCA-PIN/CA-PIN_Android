package com.caffeine.capin.network

import com.caffeine.capin.cafeti.model.RequestCafetiData
import com.caffeine.capin.cafeti.model.ResponseCafetiData
import com.caffeine.capin.category.model.RequestArchiveCafeDTO
import com.caffeine.capin.login.model.*
import com.caffeine.capin.map.dto.ResponseCafeDetail
import com.caffeine.capin.map.dto.ResponseCafeList
import com.caffeine.capin.map.dto.ResponseMyMapLocations
import com.caffeine.capin.mypage.api.request.RequestDeletePinData
import com.caffeine.capin.mypage.api.request.RequestNewCategoryData
import com.caffeine.capin.mypage.api.request.RequestProfileEditData
import com.caffeine.capin.mypage.api.request.RequestPutReviewData
import com.caffeine.capin.mypage.api.response.ResponseMyCategoryData
import com.caffeine.capin.mypage.api.response.ResponseMyData
import com.caffeine.capin.mypage.api.response.ResponseMyPinData
import com.caffeine.capin.mypage.api.response.ResponseMyReviewData
import com.caffeine.capin.network.response.CafeMenusResponse
import com.caffeine.capin.network.response.CafeReviewsResponse
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

    @DELETE("/reviews/{reviewId}")
    fun deleteMyReview(
        @Header("token") token: String,
        @Path("reviewId") reviewId: String
    ): Call<BaseResponse>

    @PUT("/reviews/{reviewId}")
    fun putMyReview(
        @Header("token") token: String,
        @Path("reviewId") reviewId: String,
        @Body body: RequestPutReviewData
    ): Call<BaseResponse>

    @GET("/cafes/myMap")
    fun getMyMapPins(
        @Query("tags") tags: List<Int?>
    ): Single<ResponseMyMapLocations>

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

    @GET("/user/myInfo")
    fun getUserProfile(): Single<ResponseMyData>

    @GET("/reviews")
    fun getCafeReviewsOf(
        @Query("cafe") cafeId: String
    ): Call<CafeReviewsResponse>

    @Multipart
    @PUT("/user/myInfo")
    fun putMyProfileEdit(
        @Header("token") token: String,
        @Part nickname: MultipartBody.Part,
        @Part profileImg: MultipartBody.Part
    ): Call<BaseResponse>
}
