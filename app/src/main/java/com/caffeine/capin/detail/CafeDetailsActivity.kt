package com.caffeine.capin.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.caffeine.capin.databinding.ActivityCafeDetailsBinding

class CafeDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityCafeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
