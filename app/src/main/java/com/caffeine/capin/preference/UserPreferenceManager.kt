package com.caffeine.capin.preference

class UserPreferenceManager(
    private val preferenceManager: PreferenceManager
    ) {
    companion object {
        private const val PREFS_KEY_USER_TOKEN = "user_token"
    }

    fun setUserToken(token: String) {
        preferenceManager.setPreferences(PREFS_KEY_USER_TOKEN, token)
    }

    fun getUserToken(): String {
        return preferenceManager.getPreferences(PREFS_KEY_USER_TOKEN, "")
    }
}