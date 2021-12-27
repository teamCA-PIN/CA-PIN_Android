package com.caffeine.capin.preference

class UserPreferenceManager(
    private val preferenceManager: PreferenceManager
) {
    fun setUserAccessToken(accessToken: String) {
        preferenceManager.setPreferences(ACCESS_TOKEN, accessToken)
    }

    fun getUserAccessToken(): String {
        return preferenceManager.getPreferences(ACCESS_TOKEN, "")
    }

    fun setUserRefreshToken(refreshToken: String) {
        preferenceManager.setPreferences(REFRESH_TOKEN, refreshToken)
    }

    fun setUserEmail(email: String) {
        preferenceManager.setPreferences(EMAIL, email)
    }

    fun getUserEmail(): String {
        return preferenceManager.getPreferences(EMAIL, "")
    }

    fun setUserPassword(password: String) {
        preferenceManager.setPreferences(PASSWORD, password)
    }

    fun getUserPassword(): String {
        return preferenceManager.getPreferences(PASSWORD, "")
    }

    fun getUserRefreshToken(): String {
        return preferenceManager.getPreferences(REFRESH_TOKEN, "")
    }

    fun setNeedCafetiCheck(check: Boolean) {
        preferenceManager.setPreferences(NEED_CAFETI_CHECK, check)
    }

    fun getNeedCafetiCheck(): Boolean {
        return preferenceManager.getPreferences(NEED_CAFETI_CHECK, false)
    }

    companion object {
        private const val REFRESH_TOKEN = "REFRESH_TOKEN"
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
        private const val NEED_CAFETI_CHECK = "NEED_CAFETI_CHECK"
        private const val EMAIL = "EMAIL"
        private const val PASSWORD = "PASSWORD"
    }
}