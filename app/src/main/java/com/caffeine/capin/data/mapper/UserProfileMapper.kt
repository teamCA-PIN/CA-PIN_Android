package com.caffeine.capin.data.mapper

import com.caffeine.capin.data.dto.mypage.ResponseMyInfoData
import com.caffeine.capin.domain.entity.profile.UserEntity

class UserProfileMapper: DTOMapper<ResponseMyInfoData, UserEntity> {
    override fun map(from: ResponseMyInfoData): UserEntity =
        UserEntity(
            from.nickname,
            from.email,
            from.pinNum,
            from.reviewNum,
            from.profileImg,
            from.cafeti.type
        )
}