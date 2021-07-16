package com.caffeine.capin.review.all

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.caffeine.capin.databinding.ActivityAllCafeReviewsBinding
import com.caffeine.capin.review.CafeReviewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllCafeReviewsActivity : AppCompatActivity() {
    private val cafeReviewsAdapter = CafeReviewsAdapter()
    private val allCafeReviewsViewModel: AllCafeReviewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAllCafeReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.viewModel = allCafeReviewsViewModel
        binding.listReviews.adapter = cafeReviewsAdapter
        binding.buttonBack.setOnClickListener { finish() }

        allCafeReviewsViewModel.cafeReviews.observe(this) {
            cafeReviewsAdapter.submitList(it)
        }
        allCafeReviewsViewModel.loadCafeDetails(getCafeId())
    }

    private fun getCafeId(): String {
        return intent.getStringExtra(KEY_CAFE_ID) ?: error("cafe id must be put in intent")
    }

    companion object {
        const val KEY_CAFE_ID = "KEY_CAFE_ID"
    }
}
