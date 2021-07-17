package com.caffeine.capin.login.ui

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.caffeine.capin.MainActivity
import com.caffeine.capin.R
import com.caffeine.capin.databinding.ActivitySplashBinding
import com.caffeine.capin.preference.UserPreferenceManager
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

        Observable.timer(2, TimeUnit.SECONDS)
            .subscribe {
                checkIsAlreadyLogin()
            }

        val fadeInAnim = AnimationUtils.loadAnimation(this, R.anim.animation_fade_in)
        binding.splashIv.startAnimation(fadeInAnim)
    }

    private fun checkIsAlreadyLogin() {
        if (!userPreferenceManager.getUserToken().isNullOrEmpty()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        finish()
    }
}