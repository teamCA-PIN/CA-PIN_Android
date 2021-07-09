package com.caffeine.capin.cafeti

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.caffeine.capin.databinding.ActivityCoffeeMenuBinding

class CoffeeMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoffeeMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoffeeMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        beforeButtonClickEvent()
        nextButtonClickEvent()

    }

    private fun beforeButtonClickEvent() {
        binding.btnBefore.setOnClickListener() {
            val intent = Intent(this@CoffeeMenuActivity, CoffeeTasteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun nextButtonClickEvent() {
        binding.btnNext.setOnClickListener() {
            Log.e("button", "${binding.radiogroupCoffeeMenu.checkedRadioButtonId}")
            if (binding.radiogroupCoffeeMenu.checkedRadioButtonId != -1) {
                //Todo: 서버통신 연결 작업
                val intent = Intent(this@CoffeeMenuActivity, CafeStyleActivity::class.java)
                startActivity(intent)
            }
            else {

            }
        }
    }
}