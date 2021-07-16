package com.caffeine.capin.profile.repository

import com.caffeine.capin.profile.model.UserEntity
import io.reactivex.rxjava3.core.Single

interface UserProfileRepository {
    fun getUserProfile(): Single<UserEntity>
}