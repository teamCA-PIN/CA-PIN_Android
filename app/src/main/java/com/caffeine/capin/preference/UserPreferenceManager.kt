package com.caffeine.capin.preference

class UserPreferenceManager(
    private val preferenceManager: PreferenceManager
    ) {
    companion object {
        private const val PREFS_KEY_USER_TOKEN = "user_token"
        private const val PREFS_NEED_CAFETI_CHECK = "need_cafeti_check"
    }

    fun setUserToken(token: String) {
        preferenceManager.setPreferences(PREFS_KEY_USER_TOKEN, token)
    }

    fun getUserToken(): String {
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI2MGVlODMzNjhiNzg1MTFhMDc5MGRjNzkiLCJpYXQiOjE2MzgxNjIxMTEsImV4cCI6MTYzODI0ODUxMX0.pC8JF1GWcs2_7g0npcLZbT4rWjzEhnbhA93bACYJ0aU"
//        return preferenceManager.getPreferences(PREFS_KEY_USER_TOKEN, "")
    }

    fun setNeedCafetiCheck(check: Boolean) {
        preferenceManager.setPreferences(PREFS_NEED_CAFETI_CHECK, check)
    }

    fun getNeedCafetiCheck(): Boolean {
        return preferenceManager.getPreferences(PREFS_NEED_CAFETI_CHECK, false)
    }
}