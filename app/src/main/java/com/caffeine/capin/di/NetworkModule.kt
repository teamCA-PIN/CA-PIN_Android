package com.caffeine.capin.di

import com.caffeine.capin.BuildConfig
import com.caffeine.capin.network.CapinApiService
import com.caffeine.capin.preference.UserPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private val baseClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideHttpClient(userPreferenceManager: UserPreferenceManager): OkHttpClient {
        val interceptor = object : Interceptor{
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request()
                    .newBuilder().addHeader("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI2MGVlODMzNjhiNzg1MTFhMDc5MGRjNzkiLCJpYXQiOjE2MzkyOTc3NDksImV4cCI6MTYzOTM4NDE0OX0.M_QHq2Lp2ZAoO-wKuiI7dkLP8-Fgl6UqlJh6qBAhLvM")
                    .build()
                return chain.proceed(request)
            }
        }
        val client =  baseClient.newBuilder().addNetworkInterceptor(interceptor).build()
        return client
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient):Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideCapinApiService(retrofit: Retrofit): CapinApiService =
        retrofit.create(CapinApiService::class.java)
}