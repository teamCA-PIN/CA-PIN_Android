package com.caffeine.capin.mypage.archivingcategory

import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.caffeine.capin.databinding.ActivityMyPageCategoryEditBinding

class MyPageCategoryEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyPageCategoryEditBinding
    private lateinit var categoryColorAdapter: CategoryColorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageCategoryEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        categoryColorAdapter.categoryColorList.addAll(
            listOf<CategoryColor>(
                CategoryColor(
                    color = "6492f5"
                ),
                CategoryColor(
                    color = "6bbc9a"
                ),
                CategoryColor(
                    color = "ffc24b"
                ),
                CategoryColor(
                    color = "816f7c"
                ),
                CategoryColor(
                    color = "ffc2d5"
                ),
                CategoryColor(
                    color = "c9d776"
                ),
                CategoryColor(
                    color = "b2b9e5"
                ),
                CategoryColor(
                    color = "ff8e8e"
                ),
                CategoryColor(
                    color = "ebeaef"
                ),
                CategoryColor(
                    color = "9dc5e8"
                )
            )
        )
        categoryColorAdapter.notifyDataSetChanged()

    }
}