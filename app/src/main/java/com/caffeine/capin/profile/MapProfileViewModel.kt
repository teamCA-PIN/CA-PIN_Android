package com.caffeine.capin.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caffeine.capin.map.entity.CafetiEntity

class MapProfileViewModel: ViewModel() {
    private val _userInfo = MutableLiveData<UserEntity>()
    val userInfo: LiveData<UserEntity>
        get() = _userInfo

    fun fetchUserInfo() {
        _userInfo.value = dummyUserInfo
    }


    companion object {
        private val cafetiEntity = CafetiEntity(
            "uiduid",
            "WBFJ",
            "https://github.com/SONPYEONGHWA.png",
            "hello i'm ENTJ"
        )

        private val dummyUserInfo = UserEntity(
            "핸드피쓰",
            "capin@teamcapin.com",
            56,
            31,
            "https://github.com/SONPYEONGHWA.png",
            cafetiEntity
        )

    }
}