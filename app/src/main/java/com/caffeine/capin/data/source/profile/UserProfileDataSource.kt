package com.caffeine.capin.data.source.profile

import com.caffeine.capin.data.dto.mypage.ResponseMyData
import io.reactivex.rxjava3.core.Single

interface UserProfileDataSource {
    fun getUserProfile(): Single<ResponseMyData>
}