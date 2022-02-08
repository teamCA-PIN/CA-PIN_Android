package com.caffeine.capin.data.network

import com.caffeine.capin.data.dto.login.RequestLoginData
import com.caffeine.capin.data.dto.login.ResponseLoginData
import com.caffeine.capin.data.dto.auth.ResponseToken
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