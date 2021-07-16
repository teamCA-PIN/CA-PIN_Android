package com.caffeine.capin.profile

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