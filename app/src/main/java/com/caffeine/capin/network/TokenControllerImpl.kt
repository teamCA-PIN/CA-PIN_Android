package com.caffeine.capin.network

import com.caffeine.capin.network.response.ResponseToken
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class TokenControllerImpl @Inject constructor(
    private val capinApiService: CapinApiService
): TokenController {
    override fun refreshUserToken(token: ResponseToken.Token): Single<ResponseToken> =
        capinApiService.refreshUserToken(token)
}