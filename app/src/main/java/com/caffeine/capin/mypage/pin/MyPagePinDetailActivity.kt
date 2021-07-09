package com.caffeine.capin.mypage.pin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.caffeine.capin.databinding.ActivityMyPagePinDetailBinding

class MyPagePinDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyPagePinDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPagePinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}