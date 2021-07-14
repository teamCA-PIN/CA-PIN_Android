package com.caffeine.capin.network

import android.text.TextUtils
import android.util.Log
import com.caffeine.capin.network.response.CapinFailResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created By Malibin
 * on 7월 15, 2021
 */

fun <T> Call<T>.enqueue(
    onSuccess: ((T) -> Unit),
    onFailure: ((CapinFailResponse) -> Unit) = {},
    onError: ((t: Throwable) -> Unit) = {},
) {
    this.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                onSuccess(response.body() ?: return)
                return
            }
            val errorBody = response.errorBody()?.string() ?: return
            val jsonObject = JSONObject(errorBody)
            val capinFailResponse = CapinFailResponse(jsonObject["message"].toString())
            onFailure(capinFailResponse)
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            onError(t)
            Log.d("MalibinDebug", TextUtils.join("\n", t.stackTrace))
        }
    })
}
