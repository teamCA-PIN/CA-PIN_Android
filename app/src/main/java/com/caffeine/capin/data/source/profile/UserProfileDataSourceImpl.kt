package com.caffeine.capin.data.source.profile

import com.caffeine.capin.data.dto.mypage.ResponseMyData
import com.caffeine.capin.data.network.CapinApiService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserProfileDataSourceImpl @Inject constructor(
    private val capinApiService: CapinApiService
): UserProfileDataSource {
    override fun getUserProfile(): Single<ResponseMyData> =
        capinApiService.getUserProfile()
}