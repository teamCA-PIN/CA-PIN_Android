package com.caffeine.capin.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.caffeine.capin.R
import com.caffeine.capin.ServiceCreator
import com.caffeine.capin.cafeti.CafetiActivity
import com.caffeine.capin.databinding.ActivityLoginBinding
import com.caffeine.capin.ServiceCreator.capinService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
                        edittext.setTextColor(ContextCompat.getColor(this@LoginActivity,
                            R.color.gray_3
                        ))
                    } else {
                        edittextCount++
                        edittext.setTextColor(ContextCompat.getColor(this@LoginActivity,
                            R.color.black
                        ))
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
                setBackgroundDrawable(ContextCompat.getDrawable(this@LoginActivity,
                    R.drawable.circle_image_view2
                ))
                isClickable = true
            }
        } else {
            binding.btnLogin.apply {
                setBackgroundColor(ContextCompat.getColor(this@LoginActivity, R.color.gray_3))
                setBackgroundDrawable(ContextCompat.getDrawable(this@LoginActivity,
                    R.drawable.circle_image_view
                ))
                isClickable = false
            }
        }
    }



    private fun loginButtonClickEvent() {
        binding.btnLogin.setOnClickListener() {
            val requestLoginData = RequestLoginData(
                email = binding.loginEdittextEmail.text.toString(),
                password = binding.loginEdittextPw.text.toString()
            )
            val call: Call<ResponseLoginData> =
                ServiceCreator.capinService.postLogin(requestLoginData)
            call.enqueue(object : Callback<ResponseLoginData> {
                override fun onResponse(
                    call: Call<ResponseLoginData>,
                    response: Response<ResponseLoginData>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@LoginActivity, "로그인 성공.", LENGTH_SHORT)
                        intent = Intent(this@LoginActivity, CafetiActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@LoginActivity, "이메일 또는 비밀번호가 잘못되었습니다.", LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<ResponseLoginData>, t: Throwable) {
                    Log.d("NetworkTest", "error:$t")
                }
            })
        }
        binding.btnLogin.setOnClickListener(){
            val requestFindPwData = RequestFindPwData(
                email = binding.loginEdittextEmail.text.toString(),
                password = binding.loginEdittextPw.text.toString()
            )
            val call: Call<ResponseFindPwData> = ServiceCreator.capinService.postFindPw(requestFindPwData)
            call.enqueue(object : Callback<ResponseFindPwData> {
                override fun onResponse(
                    call: Call<ResponseFindPwData>,
                    response: Response<ResponseFindPwData>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@LoginActivity, "비밀번호가 변경되었습니다.", LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(this@LoginActivity, "존재하지 않는 이메일 입니다.", LENGTH_SHORT)
                    }
                }

                override fun onFailure(call: Call<ResponseFindPwData>, t: Throwable) {
                    Log.d("NetworkTest", "error:$t")
                }
            })

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