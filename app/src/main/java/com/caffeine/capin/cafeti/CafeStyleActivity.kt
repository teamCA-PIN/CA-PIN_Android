package com.caffeine.capin.cafeti

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.caffeine.capin.databinding.ActivityCafeStyleBinding


class CafeStyleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCafeStyleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCafeStyleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        beforeButtonClickEvent()
        nextButtonClickEvent()

    }

    private fun beforeButtonClickEvent() {
        binding.btnBefore.setOnClickListener() {
            val intent = Intent(this@CafeStyleActivity, CoffeeMenuActivity::class.java)
            startActivity(intent)
        }
    }

    private fun nextButtonClickEvent() {
        binding.btnNext.setOnClickListener() {
            Log.e("button", "${binding.radiogroupCafeStyle.checkedRadioButtonId}")
            if (binding.radiogroupCafeStyle.checkedRadioButtonId != -1) {
                //Todo: 서버통신 연결 작업
                val intent = Intent(this@CafeStyleActivity, CafeColorActivity::class.java)
                startActivity(intent)
            } else {
            }
        }
    }
}