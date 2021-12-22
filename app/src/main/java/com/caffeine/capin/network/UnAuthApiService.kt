package com.caffeine.capin.network

import com.caffeine.capin.network.response.ResponseToken
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UnAuthApiService {
    @POST("/user/refresh")
    fun refreshUserToken(@Body requestToken: ResponseToken.Token): Call<ResponseToken>
}