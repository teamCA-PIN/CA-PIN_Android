package com.caffeine.capin.util

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
import androidx.core.content.ContextCompat
import com.caffeine.capin.R

fun Activity.transparentStatusAndNavigation(
    systemUiScrim: Int = ContextCompat.getColor(this, R.color.black) // 25% black
) {
    var systemUiVisibility = 0
    // Use a dark scrim by default since light status is API 23+
    var statusBarColor = systemUiScrim
    val winParams = window.attributes


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        statusBarColor = Color.TRANSPARENT
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        systemUiVisibility = systemUiVisibility or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.decorView.systemUiVisibility = systemUiVisibility
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        winParams.flags = winParams.flags or
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        winParams.flags = winParams.flags and
                (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION).inv()
        window.statusBarColor = statusBarColor
    }

    window.attributes = winParams
}
