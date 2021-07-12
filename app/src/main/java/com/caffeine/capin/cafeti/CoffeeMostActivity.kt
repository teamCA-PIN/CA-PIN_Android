package com.caffeine.capin.cafeti

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.caffeine.capin.databinding.ActivityCoffeeMostBinding

class CoffeeMostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoffeeMostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoffeeMostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        beforeButtonClickEvent()
        nextButtonClickEvent()


    }
    private fun beforeButtonClickEvent() {
        binding.btnBefore.setOnClickListener() {
            val intent = Intent(this@CoffeeMostActivity, CafetiActivity::class.java)
            startActivity(intent)
        }
    }

    private fun nextButtonClickEvent() {
        binding.btnNext.setOnClickListener() {
            Log.e("button", "${binding.radiogroupCoffeeMost.checkedRadioButtonId}")
            if(binding.radiogroupCoffeeMost.checkedRadioButtonId != -1) {
                when(binding.radiogroupCoffeeMost.checkedRadioButtonId) {
                    binding.radiobtnCoffee.id -> {
                        val intent = Intent(this@CoffeeMostActivity, CoffeeTasteActivity::class.java)
                        startActivity(intent)
                    }
                    binding.radiobtnNoncoffee.id -> {
                        val intent = Intent(this@CoffeeMostActivity, CoffeeMenuActivity::class.java)
                        startActivity(intent)
                    }
                }
            } else {

            }
        }
    }
}