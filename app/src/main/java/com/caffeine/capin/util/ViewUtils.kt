package com.caffeine.capin.util

import android.content.res.Resources

/**
 * Created By Malibin
 * on 7월 08, 2021
 */

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
