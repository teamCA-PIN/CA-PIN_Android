package com.caffeine.capin.detail.menus

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.caffeine.capin.BuildConfig
import com.caffeine.capin.databinding.ActivityCafeMenusBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CafeMenusActivity : AppCompatActivity() {
    private val cafeMenusViewModel: CafeMenusViewModel by viewModels()
    private val cafeMenusAdapter = CafeMenusAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityCafeMenusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.viewModel = cafeMenusViewModel
        binding.listMenus.adapter = cafeMenusAdapter
        binding.buttonClose.setOnClickListener { finish() }

        cafeMenusViewModel.cafeMenus.observe(this) {
            cafeMenusAdapter.submitList(it)
        }
        cafeMenusViewModel.loadCafeMenus(getCafeId())
    }

    private fun getCafeId(): String {
        return intent.getStringExtra(KEY_CAFE_ID) ?: error("cafe id must be put in intent")

        return if (BuildConfig.DEBUG) "60e96789868b7d75f394b00b"
        else intent.getStringExtra(KEY_CAFE_ID) ?: error("cafe id must be put in intent")
    }

    companion object {
        const val KEY_CAFE_ID = "KEY_CAFE_ID"
    }
}
