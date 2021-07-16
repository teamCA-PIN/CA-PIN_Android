package com.caffeine.capin.profile.datasource

import com.caffeine.capin.mypage.api.response.ResponseMyData
import io.reactivex.rxjava3.core.Single
import retrofit2.Call

interface UserProfileDataSource {
    fun getUserProfile(): Single<ResponseMyData>
}