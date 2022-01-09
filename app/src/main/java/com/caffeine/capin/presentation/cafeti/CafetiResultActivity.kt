package com.caffeine.capin.presentation.cafeti

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.caffeine.capin.presentation.main.MainActivity
import com.caffeine.capin.data.dto.cafeti.ResponseCafetiData
import com.caffeine.capin.databinding.ActivityCafetiResultBinding
import com.caffeine.capin.data.local.UserPreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CafetiResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCafetiResultBinding
    @Inject lateinit var userPreferenceManager: UserPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCafetiResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cafetiTestFinishButtonClickEvent()
        updateCafetiResult()

    }

    private fun updateCafetiResult() {
        if (intent.hasExtra("cafeti_result")) {
            userPreferenceManager.setNeedCafetiCheck(false)
            val cafetiResultList = intent.getSerializableExtra("cafeti_result") as ResponseCafetiData.Result
            Glide.with(binding.imageviewCafetiResult.context).load(cafetiResultList.img).into(binding.imageviewCafetiResult)
            binding.textviewCafetiModifier.text = cafetiResultList.modifier
            binding.textviewCafetiModifierDetail.text = cafetiResultList.modifierDetail
            binding.textviewCafetiIntroduction.text = cafetiResultList.introduction
            binding.textviewCafetiType.text = cafetiResultList.type
        }
    }

    private fun cafetiTestFinishButtonClickEvent() {
        binding.btnCafetitestfinish.setOnClickListener() {
            val intent = Intent(this@CafetiResultActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}