package com.caffeine.capin.profile

import com.caffeine.capin.mypage.api.response.ResponseMyInfoData
import com.caffeine.capin.network.DTOMapper

class UserProfileMapper:DTOMapper<ResponseMyInfoData, UserEntity> {
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