package com.caffeine.capin.util

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation

fun View.applyVisibilityAnimation(isUpward: Boolean, reveal: Boolean, durationTime: Long) {
    val animationSet = AnimationSet(true)
    val alphaAnimation = if (reveal) AlphaAnimation(0f, 1f) else AlphaAnimation(1f, 0f)
    val translateAnimation = when {
        isUpward && reveal -> TranslateAnimation(0f, 0f, 70f, 0f)
        isUpward && !reveal -> TranslateAnimation(0f, 0f, 0f, -height.toFloat())
        !isUpward && reveal -> TranslateAnimation(0f, 0f,  -height.toFloat(),0f)
        !isUpward && !reveal -> TranslateAnimation(0f, 0f,  0f, height.toFloat())
        else -> throw RuntimeException("error vertical appear animation")
    }

    alphaAnimation.duration = durationTime
    translateAnimation.duration = durationTime
    animationSet.addAnimation(alphaAnimation)
    animationSet.addAnimation(translateAnimation)

    animationSet.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(p0: Animation?) {}
        override fun onAnimationEnd(p0: Animation?) {
            visibility = if(reveal) View.VISIBLE else View.GONE
        }
        override fun onAnimationRepeat(p0: Animation?) {}
    })
    startAnimation(animationSet)
}