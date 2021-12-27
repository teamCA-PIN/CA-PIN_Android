package com.caffeine.capin.network

import com.caffeine.capin.login.model.RequestLoginData
import com.caffeine.capin.login.model.ResponseLoginData
import com.caffeine.capin.network.response.ResponseToken
import io.reactivex.rxjava3.core.Single
import retrofit2.Call

interface TokenController {
    fun refreshUserToken(token: ResponseToken.Token): Call<ResponseToken>
    fun login(requestLogin: RequestLoginData): Single<ResponseLoginData>
}