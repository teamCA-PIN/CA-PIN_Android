package com.caffeine.capin.mypage.mycategory

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.caffeine.capin.R
import com.caffeine.capin.databinding.ActivityMyPageCategoryEditBinding

class MyPageCategoryEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyPageCategoryEditBinding
    private lateinit var categoryColorAdapter: CategoryColorAdapter
    private lateinit var checkedColor: CategoryColor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageCategoryEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mypageCategoryEditBackBtn.setOnClickListener { onBackPressed() }

        if(intent.hasExtra("feature")) {
            binding.mypageCategoryEditHeaderTv.text = intent.getStringExtra("feature")
        } else {
            binding.mypageCategoryEditHeaderTv.text = "카테고리 편집"
        }

        binding.mypageCategoryEditDeleteBtn.setOnClickListener {
            binding.mypageCategoryEditEdt.text.clear()
            binding.mypageCategoryEditDeleteBtn.isVisible = false
        }

        binding.mypageCategoryEditEdt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.mypageCategoryEditLengthTv.text = "0/10"
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val userInput = binding.mypageCategoryEditEdt.text.toString()
                binding.mypageCategoryEditLengthTv.text = "${userInput.length.toString()}/10"
                binding.mypageCategoryEditDeleteBtn.isVisible = true
            }

            override fun afterTextChanged(s: Editable?) {
                val userInput = binding.mypageCategoryEditEdt.text.toString()
                binding.mypageCategoryEditLengthTv.text = "${userInput.length.toString()}/10"
                binding.mypageCategoryEditDeleteBtn.isVisible = true
            }
        })

        categoryColorAdapter = CategoryColorAdapter()
        binding.mypageCategoryColorRcv.adapter = categoryColorAdapter

        categoryColorAdapter.setOnSigleCheckListener(object :
            CategoryColorAdapter.OnSigleCheckListener{
            override fun onSingleCheck(categoryColor: CategoryColor) {
                checkedColor = categoryColor
                if(checkedColor in categoryColorAdapter.categoryColorList) {

                }
            }
        })

        categoryColorAdapter.categoryColorList.addAll(
            listOf<CategoryColor>(
                CategoryColor(
                    color = R.drawable.selector_category_color1
                ),
                CategoryColor(
                    color = R.drawable.selector_category_color2
                ),
                CategoryColor(
                    color = R.drawable.selector_category_color3
                ),
                CategoryColor(
                    color = R.drawable.selector_category_color4
                ),
                CategoryColor(
                    color = R.drawable.selector_category_color5
                ),
                CategoryColor(
                    color = R.drawable.selector_category_color6
                ),
                CategoryColor(
                    color = R.drawable.selector_category_color7
                ),
                CategoryColor(
                    color = R.drawable.selector_category_color8
                ),
                CategoryColor(
                    color = R.drawable.selector_category_color9
                ),
                CategoryColor(
                    color = R.drawable.selector_category_color10
                )
            )
        )
        categoryColorAdapter.notifyDataSetChanged()
    }
}