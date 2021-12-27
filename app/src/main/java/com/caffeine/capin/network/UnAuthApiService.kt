package com.caffeine.capin.network

import com.caffeine.capin.login.model.RequestLoginData
import com.caffeine.capin.login.model.ResponseLoginData
import com.caffeine.capin.network.response.ResponseToken
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UnAuthApiService {
    @POST("/user/refresh")
    fun refreshUserToken(@Body requestToken: ResponseToken.Token): Call<ResponseToken>
    @POST("/user/login")
    fun login(@Body body: RequestLoginData): Single<ResponseLoginData>

}