package com.caffeine.capin.presentation.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.caffeine.capin.presentation.main.MainActivity
import com.caffeine.capin.R
import com.caffeine.capin.presentation.cafeti.CafetiActivity
import com.caffeine.capin.presentation.customview.CapinToastMessage.createCapinRejectToast
import com.caffeine.capin.presentation.customview.CapinToastMessage.createCapinToast
import com.caffeine.capin.databinding.ActivityLoginBinding
import com.caffeine.capin.data.dto.login.RequestLoginData
import com.caffeine.capin.data.dto.login.ResponseLoginData
import com.caffeine.capin.data.network.ServiceCreator
import com.caffeine.capin.data.local.UserPreferenceManager
import com.caffeine.capin.presentation.signup.SignUpActivity
import com.caffeine.capin.util.hideKeyBoard
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var edittextCount = 0
    @Inject lateinit var userPreferenceManager: UserPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLogin.clipToOutline = true

        binding.root.run {
            setOnClickListener { hideKeyBoard()}
        }
        loginButtonClickEvent()
        signupTextClickEvent()
        findPwTextClickEvent()

        lateinit var email: EditText
        lateinit var password: EditText
        email = binding.loginEdittextEmail
        password = binding.loginEdittextPw

        val edittextList = listOf<EditText>(
            email,
            password
        )

        edittextList.forEach { edittext ->
            edittext.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(text: Editable?) {
                    if (text.isNullOrEmpty()) {
                        edittextCount--
                        edittext.setTextColor(
                            ContextCompat.getColor(
                                this@LoginActivity,
                                R.color.gray_3
                            )
                        )
                    } else {
                        edittextCount++
                        edittext.setTextColor(
                            ContextCompat.getColor(
                                this@LoginActivity,
                                R.color.black
                            )
                        )
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
                setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        this@LoginActivity,
                        R.drawable.circle_image_view2
                    )
                )
                isClickable = true
            }
        } else {
            binding.btnLogin.apply {
                setBackgroundColor(ContextCompat.getColor(this@LoginActivity, R.color.gray_3))
                setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        this@LoginActivity,
                        R.drawable.circle_image_view
                    )
                )
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
                ServiceCreator.capinApiService.postLogin(requestLoginData)
            call.enqueue(object : Callback<ResponseLoginData> {
                override fun onResponse(
                    call: Call<ResponseLoginData>,
                    response: Response<ResponseLoginData>
                ) {
                    if (response.isSuccessful) {
                        userPreferenceManager.run {
                            response.body()?.let { result ->
                                setUserAccessToken(result.loginData.token_access)
                                setUserNickName(result.loginData.nickname)
                                setUserEmail(binding.loginEdittextEmail.text.toString())
                                setUserPassword(binding.loginEdittextPw.text.toString())
                                setUserRefreshToken(result.loginData.token_refresh)
                            }
                        }

                        createCapinToast(this@LoginActivity, "로그인 성공.", 135)
                        successLogin()
                    } else {
                        createCapinRejectToast(this@LoginActivity, "이메일 또는 비밀번호가 잘못되었습니다.", 100)?.show()
                    }
                }

                override fun onFailure(call: Call<ResponseLoginData>, t: Throwable) {
                    createCapinRejectToast(this@LoginActivity, "이메일 또는 비밀번호가 잘못되었습니다.", 100)?.show()

                    Log.d("NetworkTest", "error:$t")
                }
            })
        }
    }

    private fun successLogin() {
        val isNeedToCheckCafeti = userPreferenceManager.getNeedCafetiCheck()
        if (isNeedToCheckCafeti) {
            val intent = Intent(this@LoginActivity, CafetiActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
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
            val intent = Intent(this@LoginActivity, LoginPwActivity::class.java)
            startActivity(intent)
        }

    }
}