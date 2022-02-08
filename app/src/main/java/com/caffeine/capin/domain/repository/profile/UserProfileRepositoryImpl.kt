package com.caffeine.capin.domain.repository.profile

import com.caffeine.capin.data.mapper.UserProfileMapper
import com.caffeine.capin.data.source.profile.UserProfileDataSource
import com.caffeine.capin.domain.entity.profile.UserEntity
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