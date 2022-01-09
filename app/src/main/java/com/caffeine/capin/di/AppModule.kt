package com.caffeine.capin.di

import android.content.Context
import com.caffeine.capin.data.network.AuthInterceptor
import com.caffeine.capin.data.network.TokenController
import com.caffeine.capin.data.local.UserPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenController: TokenController, userPreferenceManager: UserPreferenceManager, @ApplicationContext context: Context) =
        AuthInterceptor(tokenController, userPreferenceManager, context)

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context = context
}