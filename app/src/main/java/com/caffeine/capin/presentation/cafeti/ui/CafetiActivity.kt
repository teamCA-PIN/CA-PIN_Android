package com.caffeine.capin.presentation.cafeti.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.caffeine.capin.R
import com.caffeine.capin.databinding.ActivityCafetiBinding
import com.caffeine.capin.presentation.mypage.MyPageActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CafetiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCafetiBinding
    private lateinit var navHostController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCafetiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavController()
    }

    private fun initNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_cafeti) as NavHostFragment
        navHostController = navHostFragment.findNavController()
    }
}