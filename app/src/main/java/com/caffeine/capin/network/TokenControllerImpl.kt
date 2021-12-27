package com.caffeine.capin.network

import com.caffeine.capin.login.model.RequestLoginData
import com.caffeine.capin.login.model.ResponseLoginData
import com.caffeine.capin.network.response.ResponseToken
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import javax.inject.Inject

class TokenControllerImpl @Inject constructor(
    private val unAuthApiService: UnAuthApiService
): TokenController {
    override fun refreshUserToken(token: ResponseToken.Token): Call<ResponseToken> =
        unAuthApiService.refreshUserToken(token)

    override fun login(requestLogin: RequestLoginData): Single<ResponseLoginData> =
        unAuthApiService.login(requestLogin)

}