package com.caffeine.capin

import com.caffeine.capin.cafeti.RequestCafetiData
import com.caffeine.capin.cafeti.ResponseCafetiData
import com.caffeine.capin.login.*
import retrofit2.Call
import retrofit2.http.*

interface CapinService {
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