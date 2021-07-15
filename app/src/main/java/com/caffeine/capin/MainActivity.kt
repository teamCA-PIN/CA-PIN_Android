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
    @Inject lateinit var userPreferenceManager: UserPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setNavController()
        transparentStatusAndNavigation()

        userPreferenceManager.setUserToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI2MGU2OWUwZGNlN2Q0M2M3MzNlZTI2MTkiLCJpYXQiOjE2MjYzOTIwNTYsImV4cCI6MTYyNzI1NjA1Nn0.PUFfPsD-QU9CpM3kAV02GDM9XKIcpfdZiQEuOjR7xV4")
    }

    private fun setNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_nav_host) as NavHostFragment
        navController = navHostFragment.findNavController()
    }
}