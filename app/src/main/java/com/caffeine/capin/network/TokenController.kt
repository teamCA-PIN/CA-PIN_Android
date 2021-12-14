package com.caffeine.capin.network

import com.caffeine.capin.network.response.ResponseToken
import io.reactivex.rxjava3.core.Single

interface TokenController {
    fun refreshUserToken(token: ResponseToken.Token): Single<ResponseToken>
}