package com.caffeine.capin.category.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.caffeine.capin.category.CategoryNameEntity
import com.caffeine.capin.category.CategoryViewModel
import com.caffeine.capin.databinding.ActivitySelectCategoryBinding
import com.caffeine.capin.map.entity.CafeDetailEntity
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        archiveCafeToCategory()
    }

    private fun setToolbar() {
        binding.toolbarCafeName.setBackButton {
            finish()
        }

        if (intent.hasExtra("selected_cafe_info")) {
            val gson = Gson()
            val jsonString = intent.getStringExtra("selected_cafe_info")
            val selectedCafeInfo = gson.fromJson(jsonString, CafeDetailEntity::class.java)
            viewModel.changeSelectedCafeDetail(selectedCafeInfo)
            binding.toolbarCafeName.setToolbarTitle(selectedCafeInfo.name)
        }
    }

    private fun setCategoryListAdapter() {
        binding.recyclerviewSelectCategory.apply {
            adapter = CategoryListAdapter(object: CategoryListAdapter.CategorySelectListener{
                override fun selectCategory(categoryNameEntity: CategoryNameEntity) {
                    Log.e("category", "$categoryNameEntity")
                    viewModel.changeSelectedCategory(categoryNameEntity)
                }
            })
        }
    }

    private fun updateCategoryList() {
        viewModel.categoryList.observe(this) {
            (binding.recyclerviewSelectCategory.adapter as CategoryListAdapter).submitList(it)
        }
    }

    private fun archiveCafeToCategory() {
        binding.buttonComplete.setOnClickListener {
            viewModel.archiveCafe()
        }
    }
}