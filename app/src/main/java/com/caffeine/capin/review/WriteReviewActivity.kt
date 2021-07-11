package com.caffeine.capin.review

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.caffeine.capin.R
import com.caffeine.capin.customview.CapinDialog
import com.caffeine.capin.customview.CapinDialogBuilder
import com.caffeine.capin.customview.CapinDialogButton
import com.caffeine.capin.customview.DialogClickListener
import com.caffeine.capin.databinding.ActivityWriteReviewBinding

class WriteReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWriteReviewBinding
    private val viewModel by viewModels<WriteReviewViewModel>()
    private lateinit var pictureUri: Uri
    private val pictures = mutableListOf<PictureUriEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteReviewBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setWriteReviewToolber()
        showStagingPictureDialog()

    }

    private fun setWriteReviewToolber() {
        binding.toolbar.apply {
            setToolbarTitle("리뷰 작성하기")
            setBackButton {
                //Todo: back button 클릭시 이벤트 추가
            }
        }
    }

    //Todo: 권한 확인후 사진 촬영, 갤러리에서 사진가져오기
    private fun showStagingPictureDialog() {
        binding.buttonAddPicture.root.setOnClickListener {

            val buttonList = ArrayList<CapinDialogButton>()

            buttonList.apply {
                add(
                    CapinDialogButton("앨범에서 선택",
                        ContextCompat.getColor(this@WriteReviewActivity, R.color.pointcolor_1),this@WriteReviewActivity,
                        object : CapinDialogButton.OnClickListener {
                            override fun onClick() {

                            }
                        })
                )
                add(
                    CapinDialogButton("카메라 촬영",
                        ContextCompat.getColor(this@WriteReviewActivity, R.color.pointcolor_1),this@WriteReviewActivity,
                        object : CapinDialogButton.OnClickListener {
                            override fun onClick() {

                            }
                        })
                )
            }

            val dialog: CapinDialog = CapinDialogBuilder("사진 등록")
                .setButtonArray(buttonList)
                .setExitButton(true)
                .build()

            dialog.show(supportFragmentManager, "picture")
        }
    }


    companion object {
        private const val PERMISSION_CAMERA = android.Manifest.permission.CAMERA
        private const val PERMISSION_WRITE_EXTERNAL_STORAGE =
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        private const val PERMISSION_READ_EXTERNAL_STORAGE =
            android.Manifest.permission.READ_EXTERNAL_STORAGE

        private val PERMISSION_REQUESTED = arrayOf(
            PERMISSION_CAMERA,
            PERMISSION_WRITE_EXTERNAL_STORAGE,
            PERMISSION_READ_EXTERNAL_STORAGE
        )
    }

}