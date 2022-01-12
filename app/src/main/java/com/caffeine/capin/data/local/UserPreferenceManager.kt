package com.caffeine.capin.data.local

class UserPreferenceManager(
    private val preferenceManager: PreferenceManager
) {
    fun setUserAccessToken(accessToken: String) {
        preferenceManager.setPreferences(ACCESS_TOKEN, accessToken)
    }
    fun getUserAccessToken(): String = preferenceManager.getPreferences(ACCESS_TOKEN, "")

    fun setUserRefreshToken(refreshToken: String) {
        preferenceManager.setPreferences(REFRESH_TOKEN, refreshToken)
    }
    fun getUserRefreshToken(): String = preferenceManager.getPreferences(REFRESH_TOKEN, "")

    fun setUserNickName(nickname: String) {
        preferenceManager.setPreferences(NICKNAME, nickname)
    }

    fun getUserNickName() = preferenceManager.getPreferences(NICKNAME, "")


    fun setUserEmail(email: String) {
        preferenceManager.setPreferences(EMAIL, email)
    }
    fun getUserEmail(): String = preferenceManager.getPreferences(EMAIL, "")


    fun setUserPassword(password: String) {
        preferenceManager.setPreferences(PASSWORD, password)
    }
    fun getUserPassword(): String = preferenceManager.getPreferences(PASSWORD, "")


    fun setNeedCafetiCheck(check: Boolean) {
        preferenceManager.setPreferences(NEED_CAFETI_CHECK, check)
    }
    fun getNeedCafetiCheck(): Boolean = preferenceManager.getPreferences(NEED_CAFETI_CHECK, false)


    companion object {
        private const val REFRESH_TOKEN = "REFRESH_TOKEN"
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
        private const val NEED_CAFETI_CHECK = "NEED_CAFETI_CHECK"
        private const val EMAIL = "EMAIL"
        private const val PASSWORD = "PASSWORD"
        private const val NICKNAME = "NICKNAME"
    }
}