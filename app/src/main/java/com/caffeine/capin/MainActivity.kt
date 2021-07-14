package com.caffeine.capin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.caffeine.capin.databinding.ActivityMainBinding
import com.caffeine.capin.preference.UserPreferenceManager
import com.caffeine.capin.util.transparentStatusAndNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    @Inject lateinit var preference: UserPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setNavController()
        transparentStatusAndNavigation()
        preference.setUserToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI2MGVkNDM0YmUxMGI5ZDA3ZWQ1MmRiNzkiLCJpYXQiOjE2MjYzMDQzOTgsImV4cCI6MTYyNjM5MDc5OH0.q1Lq2jehV7mFm2zHZ96kk0lbSLn6tIYqrQ-akXSFCeg")
    }

    private fun setNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_nav_host) as NavHostFragment
        navController = navHostFragment.findNavController()

    }
}