package com.caffeine.capin.cafeti

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.caffeine.capin.databinding.ActivityCafeStyleBinding
import com.caffeine.capin.databinding.ActivityCoffeeMostBinding


class CafeStyleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCafeStyleBinding
    private lateinit var coffeebinding: ActivityCoffeeMostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCafeStyleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        beforeButtonClickEvent()
        nextButtonClickEvent()

    }

    private fun beforeButtonClickEvent() {
        binding.btnBefore.setOnClickListener() {
            when (coffeebinding.radiogroupCoffeeMost.checkedRadioButtonId) {
                coffeebinding.radiobtnCoffee.id -> {
                    val intent = Intent(this@CafeStyleActivity, CoffeeMostActivity::class.java)
                    startActivity(intent)
                }
                coffeebinding.radiobtnNoncoffee.id -> {
                    val intent = Intent(this@CafeStyleActivity, CoffeeMenuActivity::class.java)
                }

            }
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
