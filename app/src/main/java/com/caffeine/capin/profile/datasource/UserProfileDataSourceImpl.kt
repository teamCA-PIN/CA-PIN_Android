package com.caffeine.capin.profile.datasource

import com.caffeine.capin.mypage.api.response.ResponseMyData
import com.caffeine.capin.network.CapinApiService
import com.caffeine.capin.profile.datasource.UserProfileDataSource
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserProfileDataSourceImpl @Inject constructor(
    private val capinApiService: CapinApiService
): UserProfileDataSource {
    override fun getUserProfile(): Single<ResponseMyData> =
        capinApiService.getUserProfile()
}