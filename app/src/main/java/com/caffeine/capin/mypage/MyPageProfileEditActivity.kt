package com.caffeine.capin.mypage

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil.setContentView
import com.caffeine.capin.R
import com.caffeine.capin.cafeti.CafetiActivity
import com.caffeine.capin.customview.CapinDialog
import com.caffeine.capin.customview.CapinDialogBuilder
import com.caffeine.capin.customview.CapinDialogButton
import com.caffeine.capin.databinding.ActivityMyPageProfileEditBinding

class MyPageProfileEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyPageProfileEditBinding

    private val getImageLancher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            binding.profileEditProfileIv.setImageURI(result.data?.data)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.profileEditBackBtn.setOnClickListener { onBackPressed() }

        binding.profileEditProfileEditBtn.setOnClickListener {
            showEditProfileialog()
        }

        binding.profileEditNameDeleteBtn.setOnClickListener {
            binding.profileEditNameEdt.text.clear()
            binding.profileEditNameDeleteBtn.isVisible = false
        }

        binding.profileEditNameEdt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.profileEditNameLengthTv.text = "0/10"
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val userInput = binding.profileEditNameEdt.text.toString()
                binding.profileEditNameLengthTv.text = "${userInput.length.toString()}/10"
                binding.profileEditNameDeleteBtn.isVisible = true
            }

            override fun afterTextChanged(s: Editable?) {
                val userInput = binding.profileEditNameEdt.text.toString()
                binding.profileEditNameLengthTv.text = "${userInput.length.toString()}/10"
                binding.profileEditNameDeleteBtn.isVisible = true
            }
        })

        binding.profileEditCafetiBtn.setOnClickListener {
            val intent = Intent(this@MyPageProfileEditActivity, CafetiActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showEditProfileialog() {
        val profileEditList = ArrayList<CapinDialogButton>()
        val dialog: CapinDialog = CapinDialogBuilder("프로필 사진 설정")
            .setButtonArray(profileEditList)
            .setExitButton(true)
            .build()

        profileEditList.apply {
            add(
                CapinDialogButton("앨범에서 사진 선택",
                    ContextCompat.getColor(this@MyPageProfileEditActivity, R.color.maincolor_1), this@MyPageProfileEditActivity,
                    object : CapinDialogButton.OnClickListener {
                        override fun onClick() {
                            val intent = Intent(Intent.ACTION_PICK)
                            intent.type = MediaStore.Images.Media.CONTENT_TYPE
                            intent.type = "image/*"
                            getImageLancher.launch(intent)
                            dialog.dismiss()
                        }
                    })
            )
            add(
                CapinDialogButton("기본 이미지로 변경",
                    ContextCompat.getColor(this@MyPageProfileEditActivity, R.color.maincolor_1), this@MyPageProfileEditActivity,
                    object : CapinDialogButton.OnClickListener {
                        override fun onClick() {
                            dialog.dismiss()
                        }
                    })
            )
        }

        dialog.show(supportFragmentManager, "picture")
    }
}