package com.caffeine.capin.login

import com.caffeine.capin.cafeti.RequestCafetiData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/user/login")
    fun postLogin(
        @Body body: RequestLoginData
    ): Call<ResponseLoginData>

    @POST("/user/signup")
    fun postSignUp(
        @Body body: RequestSignUpData
    ): Call<ResponseSignUpData>

    @POST("/cafeti") //CAFETI
    fun postCafeti(
        @Body body: RequestCafetiData
    ): retrofit2.Call<RequestCafetiData>
}