package com.caffeine.capin.data.network

import com.caffeine.capin.data.dto.login.RequestLoginData
import com.caffeine.capin.data.dto.login.ResponseLoginData
import com.caffeine.capin.data.dto.auth.ResponseToken
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