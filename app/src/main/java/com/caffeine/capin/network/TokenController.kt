package com.caffeine.capin.network

import com.caffeine.capin.network.response.ResponseToken
import io.reactivex.rxjava3.core.Single
import retrofit2.Call

interface TokenController {
    fun refreshUserToken(token: ResponseToken.Token): Call<ResponseToken>
}