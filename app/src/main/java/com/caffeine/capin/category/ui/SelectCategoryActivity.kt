package com.caffeine.capin.category.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.caffeine.capin.category.CategoryViewModel
import com.caffeine.capin.category.model.CategoryNameEntity
import com.caffeine.capin.customview.CapinToastMessage.createCapinToast
import com.caffeine.capin.databinding.ActivitySelectCategoryBinding
import com.caffeine.capin.map.MapFragment.Companion.SELECTED_CAFE_INFO
import com.caffeine.capin.map.entity.CafeDetailEntity
import com.caffeine.capin.mypage.mycategory.MyPageCategoryEditActivity
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
        addCategory()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCategoryList()
    }

    private fun setToolbar() {
        binding.toolbarCafeName.setBackButton {
            finish()
        }

        if (intent.hasExtra(SELECTED_CAFE_INFO)) {
            val gson = Gson()
            val jsonString = intent.getStringExtra(SELECTED_CAFE_INFO)
            val selectedCafeInfo = gson.fromJson(jsonString, CafeDetailEntity::class.java)
            viewModel.changeSelectedCafeDetail(selectedCafeInfo)
            binding.toolbarCafeName.setToolbarTitle(selectedCafeInfo.name)
        }
    }

    private fun setCategoryListAdapter() {
        binding.recyclerviewSelectCategory.apply {
            adapter = CategoryListAdapter(object: CategoryListAdapter.CategorySelectListener{
                override fun selectCategory(categoryNameEntity: CategoryNameEntity) {
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
            createCapinToast(applicationContext, "카테고리에 저장되었습니다.", 130)?.show()
            finish()
        }
    }

    private fun addCategory() {
        binding.constriantlayoutAddCategory.setOnClickListener {
            val intent = Intent(this, MyPageCategoryEditActivity::class.java)
            intent.putExtra("feature", "새 카테고리")
            startActivity(intent)
        }
    }
}