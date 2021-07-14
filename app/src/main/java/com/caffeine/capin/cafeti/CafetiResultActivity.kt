package com.caffeine.capin.cafeti

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.caffeine.capin.ServiceCreator
import com.caffeine.capin.databinding.ActivityCafetiResultBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CafetiResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCafetiResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCafetiResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cafetiTestFinishButtonClickEvent()
        updateCafetiResult()

    }

    private fun updateCafetiResult() {
        if (intent.hasExtra("cafeti_result")) {
            val cafetiResultList = intent.getSerializableExtra("cafeti_result") as ResponseCafetiData.Result
            Glide.with(binding.imageViewCafetiResult.context).load(cafetiResultList.img).into(binding.imageViewCafetiResult)
            binding.textViewCafetiType.text = cafetiResultList.type
            binding.textViewCafetiModifier.text = cafetiResultList.modifier
            binding.textViewCafetiModifierDetail.text = cafetiResultList.modifierDetail
        }
    }

    private fun cafetiTestFinishButtonClickEvent() {
        binding.btnCafetitestfinish.setOnClickListener() {
            val intent = Intent(this@CafetiResultActivity, CafetiActivity::class.java)
            startActivity(intent)
        }
    }
}