package com.caffeine.capin.di

import com.caffeine.capin.BuildConfig
import com.caffeine.capin.data.network.AuthOkHttp
import com.caffeine.capin.data.network.AuthRetrofit
import com.caffeine.capin.data.network.UnAuthOkHttp
import com.caffeine.capin.data.network.UnAuthRetrofit
import com.caffeine.capin.data.network.AuthInterceptor
import com.caffeine.capin.data.network.CapinApiService
import com.caffeine.capin.data.network.UnAuthApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    @AuthOkHttp
    fun provideAuthOkHttpClientBuilder(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addNetworkInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @AuthRetrofit
    fun provideAuthRetrofit(@AuthOkHttp okHttpClient: OkHttpClient):Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideAuthCapinApiService(@AuthRetrofit retrofit: Retrofit): CapinApiService =
        retrofit.create(CapinApiService::class.java)


    @Provides
    @Singleton
    @UnAuthOkHttp
    fun provideUnAuthOkHttpClientBuilder(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addNetworkInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @UnAuthRetrofit
    fun provideUnAuthRetrofit(@UnAuthOkHttp okHttpClient: OkHttpClient):Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideUnAuthCapinApiService(@UnAuthRetrofit retrofit: Retrofit): UnAuthApiService =
        retrofit.create(UnAuthApiService::class.java)
}