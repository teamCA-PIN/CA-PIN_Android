package com.caffeine.capin.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.caffeine.capin.databinding.ActivityCafeDetailsBinding
import com.caffeine.capin.review.CafeReviewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CafeDetailsActivity : AppCompatActivity() {

    private val cafeDetailsViewModel: CafeDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityCafeDetailsBinding.inflate(layoutInflater)
        binding.viewModel = cafeDetailsViewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        val adapter = CafeReviewsAdapter()
        binding.listReviews.adapter = adapter

        cafeDetailsViewModel.cafeDetails.observe(this) {
            adapter.submitList(it.reviews)
        }
        cafeDetailsViewModel.loadCafeDetails("")
    }
}
