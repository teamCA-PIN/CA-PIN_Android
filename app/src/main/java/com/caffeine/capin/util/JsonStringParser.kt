package com.caffeine.capin.util

import com.google.gson.Gson

object JsonStringParser {
    fun parseToJsonString(T:Any):String {
        val gson = Gson()
        return gson.toJson(T)
    }
}