package com.caffeine.capin.network

import android.content.Context
import android.content.Intent
import android.util.Log
import com.caffeine.capin.CapinApplication
import com.caffeine.capin.login.ui.LoginActivity
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
    private val userPreferenceManager: UserPreferenceManager,
    private val context: Context
) : Interceptor {
    @Throws(Exception::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeaders(userPreferenceManager.getUserAccessToken())
            .build()

        val response = chain.proceed(request)
        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            response.close()
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
        ).execute()

        val tokenResponse = tokenRefreshed.body()
        if(tokenResponse == null) {
            Intent(context, LoginActivity::class.java).apply {
                this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(this)
            }
        }

        with(userPreferenceManager) {
            tokenResponse?.tokens?.let {
                if(it.accessToken.isNullOrEmpty()) {
                    setUserAccessToken("")
                    setUserRefreshToken("")
                    setNeedCafetiCheck(true)
                    Intent(context, LoginActivity::class.java).apply {
                        this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(this)
                    }
                } else {
                    setUserAccessToken(it.accessToken)
                    it.refreshToken?.let { setUserRefreshToken(it) }
                }
            }
        }

        return tokenResponse?.tokens?.accessToken
    }

    private fun Request.Builder.addHeaders(token: String) =
        this.apply { header(TOKEN, token) }

    companion object {
        private const val TOKEN = "token"
    }
}