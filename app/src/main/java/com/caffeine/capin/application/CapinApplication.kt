package com.caffeine.capin.application

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.caffeine.capin.BuildConfig
import com.naver.maps.map.NaverMapSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CapinApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        disableDarkMode()
        setMapClientId()
    }

    private fun disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }


    private fun setMapClientId() {
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient(BuildConfig.NAVER_MAP_CLIENT_ID)
    }
}