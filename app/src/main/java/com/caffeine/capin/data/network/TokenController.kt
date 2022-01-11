package com.caffeine.capin.data.network

import com.caffeine.capin.data.dto.login.RequestLoginData
import com.caffeine.capin.data.dto.login.ResponseLoginData
import com.caffeine.capin.data.dto.auth.ResponseToken
import io.reactivex.rxjava3.core.Single
import retrofit2.Call

interface TokenController {
    fun refreshUserToken(token: ResponseToken.Token): Call<ResponseToken>
    fun login(requestLogin: RequestLoginData): Single<ResponseLoginData>
}