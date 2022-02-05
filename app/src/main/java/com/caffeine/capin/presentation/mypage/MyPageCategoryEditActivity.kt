package com.caffeine.capin.presentation.mypage

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.caffeine.capin.R
import com.caffeine.capin.databinding.ActivityMyPageCategoryEditBinding
import com.caffeine.capin.data.dto.mypage.RequestNewCategoryData
import com.caffeine.capin.data.network.BaseResponse
import com.caffeine.capin.data.network.ServiceCreator
import com.caffeine.capin.data.local.UserPreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MyPageCategoryEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyPageCategoryEditBinding

    @Inject
    lateinit var userPreferenceManager: UserPreferenceManager

    lateinit var categoryName: String
    var selectedColor: Int = -1
    lateinit var categoryId: String
    lateinit var header: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageCategoryEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mypageCategoryEditBackBtn.setOnClickListener { onBackPressed() }

        setFeature()
        categoryDeleteButtonClickEvent()
        setEditTextWatcher()
        selectSingleColor()
        doneButtonClickEvent()
    }

    private fun selectSingleColor() {
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
                    binding.categoryColor1Iv -> selectedColor = 0
                    binding.categoryColor2Iv -> selectedColor = 1
                    binding.categoryColor3Iv -> selectedColor = 2
                    binding.categoryColor4Iv -> selectedColor = 3
                    binding.categoryColor5Iv -> selectedColor = 4
                    binding.categoryColor6Iv -> selectedColor = 5
                    binding.categoryColor7Iv -> selectedColor = 6
                    binding.categoryColor8Iv -> selectedColor = 7
                    binding.categoryColor9Iv -> selectedColor = 8
                    binding.categoryColor10Iv -> selectedColor = 9
                }

                categoryName = binding.mypageCategoryEditEdt.text.toString()

                if (categoryName.isNullOrBlank() || selectedColor.toString().isNullOrBlank()) {
                    binding.mypageCategoryColorDoneBtn.setImageResource(R.drawable.round_rectangle_gray_24dp)
                } else {
                    binding.mypageCategoryColorDoneBtn.setImageResource(R.drawable.round_rectangle_brown_24dp)
                }

            }
        }
    }

    private fun postNewCategoryToServer() {
        val requestNewCategoryData = RequestNewCategoryData(
            categoryName = binding.mypageCategoryEditEdt.text.toString(),
            colorIdx = selectedColor
        )

        val capinApiService = ServiceCreator.capinApiService.postNewCategory(
            userPreferenceManager.getUserAccessToken(),
            requestNewCategoryData
        )

        capinApiService.enqueue(object : Callback<BaseResponse> {
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d("fail", "error:$t")
            }

            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.d("success", "성공")
                finish()
            }
        })
    }

    private fun putMyCategoryToServer() {
        val requestNewCategoryData = RequestNewCategoryData(
            categoryName = binding.mypageCategoryEditEdt.text.toString(),
            colorIdx = selectedColor
        )

        val capinApiService = ServiceCreator.capinApiService.putMyCategory(
            userPreferenceManager.getUserAccessToken(),
            categoryId = categoryId,
            requestNewCategoryData
        )

        capinApiService.enqueue(object : Callback<BaseResponse> {
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d("fail", "error:$t")
            }

            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                Log.d("success", "성공")
                finish()
            }
        })
    }

    private fun setFeature() {
        categoryId = intent.getStringExtra("categoryId").toString()
        header = intent.getStringExtra("feature").toString()

        if (intent.hasExtra("feature")) {
            binding.mypageCategoryEditHeaderTv.text = header
        } else {
            binding.mypageCategoryEditHeaderTv.text = "카테고리 편집"
        }

        if (header == "새 카테고리") {
            binding.mypageCategoryEditEdt.hint = "새 카테고리명 입력"
        } else {
            binding.mypageCategoryEditEdt.hint = "수정할 카테고리명 입력"
        }
    }

    private fun categoryDeleteButtonClickEvent() {
        binding.mypageCategoryEditDeleteBtn.setOnClickListener {
            binding.mypageCategoryEditEdt.text.clear()
            binding.mypageCategoryEditDeleteBtn.isVisible = false
        }
    }

    private fun setEditTextWatcher() {
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

                if (userInput.isNotEmpty() && selectedColor > 0) {
                    binding.mypageCategoryColorDoneBtn.setImageResource(R.drawable.round_rectangle_brown_24dp)
                } else {
                    binding.mypageCategoryColorDoneBtn.setImageResource(R.drawable.round_rectangle_gray_24dp)
                }
            }
        })
    }

    private fun doneButtonClickEvent() {
        binding.mypageCategoryColorDoneBtn.setOnClickListener {
            if (intent.getStringExtra("feature") == "새 카테고리") {
                postNewCategoryToServer()
            } else {
                putMyCategoryToServer()
            }
        }
    }
}