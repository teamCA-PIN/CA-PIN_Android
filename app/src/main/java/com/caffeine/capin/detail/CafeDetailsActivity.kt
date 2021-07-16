package com.caffeine.capin.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.caffeine.capin.BuildConfig
import com.caffeine.capin.R
import com.caffeine.capin.databinding.ActivityCafeDetailsBinding
import com.caffeine.capin.detail.menus.CafeMenusActivity
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

        binding.buttonMenus.setOnClickListener { deployMenusActivity() }
        binding.buttonBack.setOnClickListener { finish() }
//        binding.toolbar.setNavigationIcon(R.drawable.icon_back_black)
//        binding.toolbar.setNavigationOnClickListener { finish() }

//
//        cafeDetailsViewModel.cafeDetails.observe(this) {
//            adapter.submitList(it.reviews)
//        }
        cafeDetailsViewModel.loadCafeDetails(getCafeId())
    }

    private fun getCafeId(): String {
        return intent.getStringExtra(KEY_CAFE_ID) ?: error("cafe id must be put in intent")
    }

    private fun deployMenusActivity() {
        Intent(this, CafeMenusActivity::class.java)
            .apply { putExtra(CafeMenusActivity.KEY_CAFE_ID, getCafeId()) }
            .also { startActivity(it) }
    }

    companion object {
        const val KEY_CAFE_ID = "KEY_CAFE_ID"
    }
}
