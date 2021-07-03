package com.caffeine.capin.preference

import android.content.SharedPreferences
import java.lang.IllegalArgumentException

class PreferenceManager(
    private val prefs: SharedPreferences
    ) {
    fun <T> setPreferences(key: String, value: T) = with(prefs.edit()) {
        when (value) {
            is String -> putString(key, value)
            is Long -> putLong(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            else -> IllegalArgumentException("Preferences type error")
        }
        apply()
    }

    fun <T> getPreferences(key: String, defaultValue: T): T {
        prefs.run {
            return when (defaultValue) {
                is String -> getString(key, defaultValue) as T
                is Int -> getInt(key, defaultValue) as T
                is Long -> getLong(key, defaultValue) as T
                is Boolean -> getBoolean(key, defaultValue) as T
                is Float -> getFloat(key, defaultValue) as T
                else -> throw IllegalArgumentException("Preferences Type Error")
            }
        }
    }
}