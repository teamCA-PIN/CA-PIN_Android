package com.caffeine.capin.login.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
import com.caffeine.capin.MainActivity
import com.caffeine.capin.databinding.ActivitySplashBinding
import com.caffeine.capin.preference.UserPreferenceManager
import com.caffeine.capin.util.applyVisibilityAnimation
import com.caffeine.capin.util.transparentStatusAndNavigation
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    @Inject lateinit var userPreferenceManager: UserPreferenceManager
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        transparentStatusAndNavigation()
        setSplashAnimation()

    }

    private fun setSplashAnimation() {
        val animationSet = AnimationSet(true)
        val alphaAnimation = AlphaAnimation(0f, 1f)
        val translateAnimation = TranslateAnimation(0f, 0f, 70f, 0f)
        animationSet.duration = 1000
        animationSet.addAnimation(alphaAnimation)
        animationSet.addAnimation(translateAnimation)
        animationSet.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {
                binding.imageviewSplash.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation?) {
                checkIsAlreadyLogin()
            }
        })
        binding.imageviewSplash.startAnimation(animationSet)
    }

    private fun checkIsAlreadyLogin() {
        if (!userPreferenceManager.getUserAccessToken().isNullOrEmpty()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        finish()
    }
}