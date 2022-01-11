package com.caffeine.capin.domain.repository.profile

import com.caffeine.capin.domain.entity.profile.UserEntity
import io.reactivex.rxjava3.core.Single

interface UserProfileRepository {
    fun getUserProfile(): Single<UserEntity>
}