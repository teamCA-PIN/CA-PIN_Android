package com.caffeine.capin.network

import com.caffeine.capin.network.response.ResponseToken
import com.caffeine.capin.preference.UserPreferenceManager
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.net.HttpURLConnection
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenController: TokenController,
    private val userPreferenceManager: UserPreferenceManager
) : Interceptor {
    @Throws(Exception::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeaders("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI2MWFiNzZhNDQ5MDJlMDQ4OGJhN2JmOTgiLCJpYXQiOjE2Mzk0NTg3OTUsImV4cCI6MTYzOTU0NTE5NX0.ZK8_tqU0o6R3tmZAIEvv6PQ0LqdWhPiMBo_sanz-r9M")
            .build()

        val response = chain.proceed(request)

        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            val newAccessToken = refreshAccessToken()
            val newRequest = chain.request().newBuilder()
                .addHeaders(newAccessToken?:"")
                .build()

            return chain.proceed(newRequest)
        }
        return response
    }

    private fun refreshAccessToken():String? {
        val tokenRefreshed = tokenController.refreshUserToken(
            ResponseToken.Token(
                userPreferenceManager.getUserAccessToken(),
                userPreferenceManager.getUserRefreshToken()
            )
        ).subscribeOn(Schedulers.io())

        val tokenResponse = tokenRefreshed.onErrorReturnItem(
            ResponseToken("", ResponseToken.Token("", ""))
        ).blockingGet()

        with(userPreferenceManager) {
            if(tokenResponse?.tokens?.accessToken.isNullOrEmpty()) {
                setUserAccessToken("")
                setUserRefreshToken("")
            } else {
                setUserAccessToken(tokenResponse?.tokens?.accessToken.toString())
                setUserRefreshToken(tokenResponse?.tokens?.refreshToken.toString())
            }
        }

        return tokenResponse?.tokens?.refreshToken
    }

    private fun Request.Builder.addHeaders(token: String) =
        this.apply { header(AUTH_HEADER_KEY, token) }

    companion object {
        private const val AUTH_HEADER_KEY = "token"
        const val ROLE_TRAINER = "ROLE_TRAINER"
        const val ROLE_CLIENT = "ROLE_CLIENT"
    }

}