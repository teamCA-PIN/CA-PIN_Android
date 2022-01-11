package com.caffeine.capin.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.caffeine.capin.data.local.PreferenceManager
import com.caffeine.capin.data.local.UserPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SharedPrefsModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        EncryptedSharedPreferences.create(
            PREFS_NAME,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    @Provides
    @Singleton
    fun providePreferenceManager(prefs: SharedPreferences): PreferenceManager =
        PreferenceManager(prefs)

    @Provides
    @Singleton
    fun provideUserPreferenceManager(preferenceManager: PreferenceManager): UserPreferenceManager =
        UserPreferenceManager(preferenceManager)

    private const val PREFS_NAME = "Capin_SharedPreferences"
}