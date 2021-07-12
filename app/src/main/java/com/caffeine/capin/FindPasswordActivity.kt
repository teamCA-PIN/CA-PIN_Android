package com.caffeine.capin

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.caffeine.capin.databinding.ActivityFindPasswordBinding
import com.caffeine.capin.login.LoginActivity

class FindPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFindPasswordBinding
    private var edittextCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnChange.clipToOutline = true
        binding.btnProve.clipToOutline = true

        numberButtonClickEvent()
        changePwButtonClickEvent()

        lateinit var number : EditText
        lateinit var newpassword : EditText
        lateinit var newpasswordcheck : EditText
        number = binding.edittextNumber
        newpassword = binding.edittextNewpw
        newpasswordcheck = binding.edittextNewpwcheck

        val edittextList = listOf<EditText>(
            number,
            newpassword,
            newpasswordcheck
        )

        edittextList.forEach{ edittext ->
            edittext.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(text: Editable?) {
                    if(text.isNullOrEmpty()) {
                        edittextCount--
                        edittext.setTextColor(ContextCompat.getColor(this@FindPasswordActivity, R.color.gray_3))
                    } else {
                        edittextCount++
                        edittext.setTextColor(ContextCompat.getColor(this@FindPasswordActivity, R.color.black))
                    }
                }
            })
        }
    }


    private fun numberButtonClickEvent() {
        binding.btnProve.setOnClickListener() {
            val number = binding.edittextNumber.text
            if (number.isNullOrBlank()) {
              //인증번호가 틀리다면
            }
            else { //인증번호가 맞다면
                Toast.makeText(this@FindPasswordActivity, "인증되었습니다.", LENGTH_SHORT).show()
            }
        }
    }

    private fun changePwButtonClickEvent() {
        binding.btnChange.setOnClickListener() {
            val password = binding.edittextNewpw.text
            val newpassword = binding.edittextNewpwcheck.text
            if (password.isNullOrBlank() || newpassword.isNullOrBlank()) {
                //비번이 틀리다면
            }
            else {
                Toast.makeText(this@FindPasswordActivity, "인증되었습니다.", LENGTH_SHORT).show()
                val intent = Intent(this@FindPasswordActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }


}