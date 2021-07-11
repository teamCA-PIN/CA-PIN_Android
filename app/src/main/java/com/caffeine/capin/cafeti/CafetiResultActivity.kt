package com.caffeine.capin.cafeti

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.caffeine.capin.databinding.ActivityCafetiResultBinding

class CafetiResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCafetiResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCafetiResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cafetiTestFinishButtonClickEvent()

    }
    private fun cafetiTestFinishButtonClickEvent() {
        binding.btnCafetitestfinish.setOnClickListener() {
            val intent = Intent(this@CafetiResultActivity, CafetiActivity::class.java)
            startActivity(intent)
        }
    }
}