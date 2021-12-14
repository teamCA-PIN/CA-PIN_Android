package com.caffeine.capin.di

import com.caffeine.capin.network.AuthInterceptor
import com.caffeine.capin.network.TokenController
import com.caffeine.capin.preference.UserPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.core.Single
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenController: TokenController, userPreferenceManager: UserPreferenceManager) =
        AuthInterceptor(tokenController, userPreferenceManager)
}