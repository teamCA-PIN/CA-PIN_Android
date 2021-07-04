package com.caffeine.capin

import android.app.Application
import com.naver.maps.map.NaverMapSdk

class CapinApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        setMapClientId()
    }

    private fun setMapClientId() {
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient(BuildConfig.NAVER_MAP_CLIENT_ID)
    }
}