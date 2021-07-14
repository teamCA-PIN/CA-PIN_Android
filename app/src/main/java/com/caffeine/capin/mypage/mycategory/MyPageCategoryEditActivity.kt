package com.caffeine.capin.mypage.mycategory

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ImageView
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.caffeine.capin.R
import com.caffeine.capin.databinding.ActivityMyPageCategoryEditBinding

class MyPageCategoryEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyPageCategoryEditBinding

    lateinit var categoryName : String
    lateinit var selectedColor : String

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

        selectSingleColor()

        binding.mypageCategoryColorDoneBtn.setOnClickListener {
            val intent = Intent()
            intent.putExtra("categoryName", categoryName)
            intent.putExtra("categoryColor", selectedColor)
            Log.d("리미-edit",categoryName)
            Log.d("리미-edit",selectedColor)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    fun selectSingleColor() {
        var currentColor = binding.categoryColor1Iv
        var previousColor = binding.categoryColor2Iv

        listOf<RadioButton>(
            binding.categoryColor1Iv,
            binding.categoryColor2Iv,
            binding.categoryColor3Iv,
            binding.categoryColor4Iv,
            binding.categoryColor5Iv,
            binding.categoryColor6Iv,
            binding.categoryColor7Iv,
            binding.categoryColor8Iv,
            binding.categoryColor9Iv,
            binding.categoryColor10Iv
        ).forEach {
            it.setOnClickListener {
                previousColor = currentColor
                previousColor.isChecked = false
                currentColor = it as RadioButton
                currentColor.isChecked = true

                when (it) {
                    binding.categoryColor1Iv -> selectedColor = "6492f5"
                    binding.categoryColor2Iv -> selectedColor = "6bbc9a"
                    binding.categoryColor3Iv -> selectedColor = "ffc24b"
                    binding.categoryColor4Iv -> selectedColor = "816f7c"
                    binding.categoryColor5Iv -> selectedColor = "ffc2d5"
                    binding.categoryColor6Iv -> selectedColor = "c9d776"
                    binding.categoryColor7Iv -> selectedColor = "b2b9e5"
                    binding.categoryColor8Iv -> selectedColor = "ff8e8e"
                    binding.categoryColor9Iv -> selectedColor = "ebeaef"
                    binding.categoryColor10Iv -> selectedColor = "9dc5e8"
                }

                categoryName = binding.mypageCategoryEditEdt.text.toString()

                if (categoryName.isNullOrBlank() || selectedColor.isNullOrBlank()) {
                    binding.mypageCategoryColorDoneBtn.setImageResource(R.drawable.round_rectangle_gray_24dp)
                } else {
                    binding.mypageCategoryColorDoneBtn.setImageResource(R.drawable.round_rectangle_brown_24dp)
                }
            }
        }
    }
}