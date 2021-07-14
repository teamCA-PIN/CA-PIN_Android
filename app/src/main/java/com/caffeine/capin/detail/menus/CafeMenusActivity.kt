package com.caffeine.capin.detail.menus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.caffeine.capin.databinding.ActivityCafeMenusBinding

class CafeMenusActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityCafeMenusBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
