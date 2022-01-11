package com.caffeine.capin.presentation.cafeti

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.caffeine.capin.databinding.ActivityCafetiBinding
import com.caffeine.capin.presentation.mypage.MyPageActivity

class CafetiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCafetiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCafetiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cafetiTestStartButtonClickEvent()
        cafetiTestCloseButtonClickEvent()
    }
    private fun cafetiTestStartButtonClickEvent() {
        binding.btnCafetiteststart.setOnClickListener() {
            val intent = Intent(this@CafetiActivity, CoffeeMostActivity::class.java)
            startActivity(intent)
        }
    }
    private fun cafetiTestCloseButtonClickEvent() {
        binding.btnclose.setOnClickListener() {
            val intent = Intent(this@CafetiActivity, MyPageActivity::class.java)
            startActivity(intent)
        }
    }
}