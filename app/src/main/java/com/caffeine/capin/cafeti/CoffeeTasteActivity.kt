package com.caffeine.capin.cafeti

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.caffeine.capin.databinding.ActivityCoffeeTasteBinding

class CoffeeTasteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoffeeTasteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoffeeTasteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        beforeButtonClickEvent()
        nextButtonClickEvent()

    }

    private fun beforeButtonClickEvent() {
        binding.btnBefore.setOnClickListener() {
            val intent = Intent(this@CoffeeTasteActivity, CoffeeMostActivity::class.java)
            startActivity(intent)
        }
    }

    private fun nextButtonClickEvent() {
        binding.btnNext.setOnClickListener() {
            Log.e("button", "${binding.radiogroupCoffeeTaste.checkedRadioButtonId}")
            if (binding.radiogroupCoffeeTaste.checkedRadioButtonId != -1) {
                //Todo: 서버통신 연결 작업
                val intent = Intent(this@CoffeeTasteActivity, CoffeeMenuActivity::class.java)
                startActivity(intent)
            }
            else {

            }
        }
    }
}