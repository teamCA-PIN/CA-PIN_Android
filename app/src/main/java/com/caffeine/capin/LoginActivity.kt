package com.caffeine.capin

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.caffeine.capin.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var edittextCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLogin.clipToOutline = true

        loginButtonClickEvent()
        signupTextClickEvent()
        findPwTextClickEvent()

        lateinit var email : EditText
        lateinit var password : EditText
        email = binding.loginEdittextEmail
        password = binding.loginEdittextPw

        val edittextList = listOf<EditText>(
            email,
            password
        )

        edittextList.forEach{ edittext ->
            edittext.addTextChangedListener(object : TextWatcher{
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
                        edittext.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.gray_3))
                    } else {
                        edittextCount++
                        edittext.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.black))
                    }
                    checkEditTextEmpty()
                }
            })
        }
    }


    private fun checkEditTextEmpty() {
        if (edittextCount >= 2) {
            binding.btnLogin.apply {
                setBackgroundColor(ContextCompat.getColor(this@LoginActivity, R.color.pointcolor_1))
                setBackgroundDrawable(ContextCompat.getDrawable(this@LoginActivity,R.drawable.circle_image_view2))
                isClickable = true
            }
        } else {
            binding.btnLogin.apply {
                setBackgroundColor(ContextCompat.getColor(this@LoginActivity, R.color.gray_3))
                setBackgroundDrawable(ContextCompat.getDrawable(this@LoginActivity,R.drawable.circle_image_view))
                isClickable = false
            }
        }
    }



    private fun loginButtonClickEvent() {
        binding.btnLogin.setOnClickListener() {
            val email = binding.loginEdittextEmail.text
            val password = binding.loginEdittextPw.text


            if (email.isNullOrBlank() || password.isNullOrBlank()) {
                Toast.makeText(this@LoginActivity, "이메일 또는 비밀번호가 잘못되었습니다.",LENGTH_SHORT).show()
            }
            else{
                //회원가입 서버 통신

            }
        }
    }

    private fun signupTextClickEvent() {
        binding.loginTextviewSignup.setOnClickListener() {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun findPwTextClickEvent() {
        binding.loginTextviewFindpw.setOnClickListener() {
            val intent = Intent(this@LoginActivity, FindPasswordActivity::class.java)
            startActivity(intent)
        }

    }


}