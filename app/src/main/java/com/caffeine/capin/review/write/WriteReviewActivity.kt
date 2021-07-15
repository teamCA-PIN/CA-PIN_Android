package com.caffeine.capin.review.write

import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.widget.CompoundButton
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.caffeine.capin.R
import com.caffeine.capin.customview.CapinDialog
import com.caffeine.capin.customview.CapinDialogBuilder
import com.caffeine.capin.customview.CapinDialogButton
import com.caffeine.capin.customview.CapinToastMessage.createCapinRejectToast
import com.caffeine.capin.databinding.ActivityWriteReviewBinding
import com.caffeine.capin.PictureUriEntity
import com.caffeine.capin.util.FormDataUtil.getBody
import com.caffeine.capin.util.FormDataUtil.getImageBody
import com.caffeine.capin.util.HorizontalItemDecoration
import com.caffeine.capin.util.JsonStringParser.parseToJsonString
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okio.BufferedSink
import okio.source
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import javax.inject.Inject

@AndroidEntryPoint
class WriteReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWriteReviewBinding
    private val viewModel by viewModels<WriteReviewViewModel>()
    private lateinit var pictureUri: Uri
    private var failedPermissions = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setWriteReviewToolber()
        showStagingPictureDialog()
        stagePictures()
        switchButtonActivation()
        postReview()
        goToMapView()
    }

    private fun setWriteReviewToolber() {
        binding.toolbar.apply {
            setToolbarTitle("리뷰 작성하기")
            setBackButton {
                //Todo: back button 클릭시 이벤트 추가
            }
        }
    }

    private fun showStagingPictureDialog() {
        val buttonList = ArrayList<CapinDialogButton>()
        val dialog: CapinDialog = CapinDialogBuilder("사진 등록")
            .setButtonArray(buttonList)
            .setExitButton(true)
            .build()

        buttonList.apply {
            add(
                CapinDialogButton("앨범에서 선택",
                    ContextCompat.getColor(this@WriteReviewActivity, R.color.pointcolor_1),this@WriteReviewActivity,
                    object : CapinDialogButton.OnClickListener {
                        override fun onClick() {
                            if(viewModel.checkImagesCount()) {
                                checkGalleryPermissions()
                            } else {
                                createCapinRejectToast(this@WriteReviewActivity, "사진은 5장까지 추가가능합니다.", 200)?.show()
                            }
                            dialog.dismiss()
                        }
                    })
            )
            add(
                CapinDialogButton("카메라 촬영",
                    ContextCompat.getColor(this@WriteReviewActivity, R.color.pointcolor_1),this@WriteReviewActivity,
                    object : CapinDialogButton.OnClickListener {
                        override fun onClick() {
                            if(viewModel.checkImagesCount()) {
                                checkCameraPermissions()
                            } else {
                                createCapinRejectToast(this@WriteReviewActivity, "최대 5장까지 추가가능합니다.", 200)?.show()
                            }
                            dialog.dismiss()
                        }
                    })
            )
        }

        binding.buttonAddPicture.root.setOnClickListener {
            dialog.show(supportFragmentManager, "picture")
        }
    }

    private fun checkCameraPermissions() {
        TAKE_PICTURE_PERMISSIONS.forEach { permission ->
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                failedPermissions.add(permission)
                requestCameraPermission.launch(permission)
            }
        }
        if (failedPermissions.isEmpty()) {
            takePicture()
        }
    }

    private fun checkGalleryPermissions() {
        if (ContextCompat.checkSelfPermission(this, PERMISSION_READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestGalleryPermission.launch(PERMISSION_READ_EXTERNAL_STORAGE)
        } else {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            intent.type = "image/*"
            getHousePicture.launch(intent)
        }
    }

    private val requestCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted ->
            if(isGranted) {
                takePicture()
            }
        }

    private val requestGalleryPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted ->
            if (isGranted) {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = MediaStore.Images.Media.CONTENT_TYPE
                intent.type = "image/*"
                getHousePicture.launch(intent)
            }
        }

    private fun takePicture() {
        val photoFile = File.createTempFile("IMG_", ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES))
        pictureUri = FileProvider.getUriForFile(this, "${packageName}.provider", photoFile)
        cameraActivityLauncher.launch(pictureUri)
    }

    private val cameraActivityLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSaved ->
        if(isSaved) {
            viewModel.addImagesOfCafe(PictureUriEntity(pictureUri))
        }
    }

    private val getHousePicture = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if(result.data != null) {
            Log.e("gallery", "${result.data}")
            viewModel.addImagesOfCafe(PictureUriEntity(result.data?.data))
        }
    }

    private fun stagePictures() {
        binding.recyclerviewPicture.apply {
            isNestedScrollingEnabled = false
            adapter = WriteReviewPictureAdapter(object: WriteReviewPictureAdapter.DeleteListener {
                override fun delete(pictureUriEntity: PictureUriEntity) {
                    viewModel.deleteImageOfCafe(pictureUriEntity)
                }
            })
            addItemDecoration(HorizontalItemDecoration(5))
        }

        viewModel.imagesOfCafe.observe(this) {
            (binding.recyclerviewPicture.adapter as WriteReviewPictureAdapter).apply {
                submitList(it)
                //Todo: 왜 notifyDataSetChanged로 갱신 안하면 안될까,,,
                notifyDataSetChanged()
            }
        }
    }

    private fun switchButtonActivation() {
        viewModel.contentsOfReview.observe(this) {
            if (viewModel.switchPostButtonActivation()) {
                binding.buttonPostReview.activeButton()
            } else {
                binding.buttonPostReview.inactiveButton()
            }
        }

        viewModel.rateOfReview.observe(this) {
            if (viewModel.switchPostButtonActivation()) {
                binding.buttonPostReview.activeButton()
            } else {
                binding.buttonPostReview.inactiveButton()
            }
        }
    }

    private fun postReview() {
        binding.buttonPostReview.setOnClickListener {
            viewModel.changeCheckedRecommend(mapOf<CompoundButton, Int>(
                binding.checkboxTaste to 1,
                binding.checkboxFeeling to 0
            ))
           viewModel.uploadReview(contentResolver)
        }
    }

    private fun goToMapView() {
        viewModel.successPost.observe(this) { success ->
            if (success) finish()
        }
    }

    companion object {
        private const val PERMISSION_CAMERA = android.Manifest.permission.CAMERA
        private const val PERMISSION_WRITE_EXTERNAL_STORAGE = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        private const val PERMISSION_READ_EXTERNAL_STORAGE = android.Manifest.permission.READ_EXTERNAL_STORAGE
        private val TAKE_PICTURE_PERMISSIONS = arrayOf(
            PERMISSION_CAMERA,
            PERMISSION_WRITE_EXTERNAL_STORAGE
        )
    }

}