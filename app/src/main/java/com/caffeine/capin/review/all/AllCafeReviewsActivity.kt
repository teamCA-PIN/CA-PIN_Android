package com.caffeine.capin.review.all

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.caffeine.capin.databinding.ActivityAllCafeReviewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllCafeReviewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAllCafeReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
