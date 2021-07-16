package com.caffeine.capin.profile

import io.reactivex.rxjava3.core.Single

interface UserProfileRepository {
    fun getUserProfile(): Single<UserEntity>
}