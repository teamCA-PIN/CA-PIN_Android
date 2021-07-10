package com.caffeine.capin.mypage

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil.setContentView
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
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            intent.type = "image/*"
            getImageLancher.launch(intent)
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
    }
}