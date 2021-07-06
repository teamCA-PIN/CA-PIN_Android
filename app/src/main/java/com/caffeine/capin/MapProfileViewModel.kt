package com.caffeine.capin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapProfileViewModel: ViewModel() {
    private val _userInfo = MutableLiveData<UserEntity>()
    val userInfo: LiveData<UserEntity>
        get() = _userInfo

    fun getUserInfo() {
        _userInfo.value = DummyUserInfo
    }


    companion object {
        private val cafetiEntity = CafetiEntity(
            "uiduid",
            "WBFJ",
            "https://github.com/SONPYEONGHWA.png",
            "hello i'm ENTJ"
        )

        private val DummyUserInfo = UserEntity(
            "핸드피쓰",
            "capin@teamcapin.com",
            56,
            31,
            "https://github.com/SONPYEONGHWA.png",
            cafetiEntity
        )

    }
}