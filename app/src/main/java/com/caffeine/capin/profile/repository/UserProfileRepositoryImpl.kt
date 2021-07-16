package com.caffeine.capin.profile.repository

import com.caffeine.capin.profile.UserProfileMapper
import com.caffeine.capin.profile.datasource.UserProfileDataSource
import com.caffeine.capin.profile.model.UserEntity
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(
    private val userProfileDataSource: UserProfileDataSource,
    private val userProfileMaper: UserProfileMapper
): UserProfileRepository {
    override fun getUserProfile(): Single<UserEntity> =
        userProfileDataSource.getUserProfile().map {
            userProfileMaper.map(it.myInfo)
        }
}