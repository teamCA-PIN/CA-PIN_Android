package com.caffeine.capin


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.caffeine.capin.databinding.ActivitySignupBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private var edittextCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSignup.clipToOutline = true

        signUpButtonClickEvent()

        lateinit var username : EditText
        lateinit var email : EditText
        lateinit var password : EditText
        lateinit var newpassword : EditText
        username = binding.edittextName
        email = binding.edittextEmail
        password = binding.edittextPw
        newpassword = binding.edittextPwagain

        val edittextList = listOf<EditText>(
            username,
            email,
            password,
            newpassword
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
                        edittext.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.gray_3))
                    } else {
                        edittextCount++
                        edittext.setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.black))
                    }
                    checkEditTextEmpty()
                }
            })
        }

    }

    private fun checkEditTextEmpty() {
        if (edittextCount >= 4) {
            binding.btnSignup.apply {
                setBackgroundColor(ContextCompat.getColor(this@SignUpActivity, R.color.pointcolor_1))
                setBackgroundDrawable(ContextCompat.getDrawable(this@SignUpActivity,R.drawable.circle_image_view2))
                isClickable = true
            }
        } else {
            binding.btnSignup.apply {
                setBackgroundColor(ContextCompat.getColor(this@SignUpActivity, R.color.gray_3))
                setBackgroundDrawable(ContextCompat.getDrawable(this@SignUpActivity,R.drawable.circle_image_view))
                isClickable = false
            }
        }
    }





    private fun signUpButtonClickEvent() {
        binding.btnSignup.setOnClickListener() {
            val username = binding.edittextName.text
            val email = binding.edittextEmail.text
            val password = binding.edittextPw.text
            val newpassword = binding.edittextPwagain.text
            if (username.isNullOrBlank() || email.isNullOrBlank() || password.isNullOrBlank() || newpassword.isNullOrBlank()) {
                Toast.makeText(this@SignUpActivity, "비밀번호가 일치하지 않습니다.", LENGTH_SHORT).show()
            }
            else{

            }
        }


}



}