package com.caffeine.capin.category

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.caffeine.capin.databinding.ActivitySelectCategoryBinding

class SelectCategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectCategoryBinding
    private val viewModel by viewModels<CategoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectCategoryBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        setCategoryListAdapter()
        updateCategoryList()
        setToolbar()
    }

    private fun setToolbar() {
        viewModel.changeCafeName("카페 머시기")
        binding.toolbarCafeName.setBackButton {

        }

        if (intent.hasExtra("CafeName")) {
            binding.toolbarCafeName.setToolbarTitle(intent.getStringExtra("CafeName")!!)
        }

    }

    private fun setCategoryListAdapter() {
        binding.recyclerviewSelectCategory.apply {
            adapter = CategoryListAdapter()
        }
    }

    private fun updateCategoryList() {
        viewModel.categoryList.observe(this) {
            (binding.recyclerviewSelectCategory.adapter as CategoryListAdapter).submitList(it)
        }
    }
}