package com.caffeine.capin.login

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/user/login")
    fun postLogin(
        @Body body: RequestLoginData
    ): Call<ResponseLoginData>
}