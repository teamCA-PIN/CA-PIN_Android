package com.caffeine.capin.presentation.review.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.CompoundButton
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.caffeine.capin.R
import com.caffeine.capin.presentation.customview.CapinDialog
import com.caffeine.capin.presentation.customview.CapinDialogBuilder
import com.caffeine.capin.presentation.customview.CapinDialogButton
import com.caffeine.capin.presentation.customview.CapinToastMessage.createCapinRejectToast
import com.caffeine.capin.databinding.ActivityWriteReviewBinding
import com.caffeine.capin.data.dto.mypage.MyReview
import com.caffeine.capin.data.local.UserPreferenceManager
import com.caffeine.capin.presentation.review.viewmodel.WriteReviewViewModel
import com.caffeine.capin.domain.entity.review.PictureUriEntity
import com.caffeine.capin.presentation.util.HorizontalItemDecoration
import com.caffeine.capin.presentation.util.UiState
import com.caffeine.capin.util.hideKeyBoard
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class WriteReviewActivity : AppCompatActivity() {
    @Inject lateinit var userPreferenceManager: UserPreferenceManager
    private lateinit var binding: ActivityWriteReviewBinding
    private val viewModel by viewModels<WriteReviewViewModel>()
    private lateinit var pictureUri: Uri
    private var failedPermissions = ArrayList<String>()
    private var tagCheckBoxList = mapOf<CompoundButton, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteReviewBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        tagCheckBoxList = mapOf(
            binding.checkboxFeeling to 0,
            binding.checkboxTaste to 1
        )

        setWriteReviewToolber()
        showStagingPictureDialog()
        stagePictures()
        switchButtonActivation()
        postReview()
        goToMapView()
        settingBeforeReview()
        hideKeyBoard()
    }

    private fun setWriteReviewToolber() {
        binding.toolbar.apply {
            if (intent.hasExtra(EDIT_REVIEW)) {
                setToolbarTitle("리뷰 수정하기")
            } else if (intent.hasExtra(KEY_CAFE_ID)) {
                setToolbarTitle("리뷰 작성하기")
                intent.getStringExtra(KEY_CAFE_ID)?.let { viewModel.changeCafeId(it) }
            }

            setBackButton {
                finish()
            }
        }
    }

    private fun settingBeforeReview() {
        if (intent.hasExtra(EDIT_REVIEW)) {
            val gson = Gson()
            val review = gson.fromJson(intent.getStringExtra(EDIT_REVIEW), MyReview::class.java)
            review.recommend.forEach { tag ->
                tagCheckBoxList.keys.find {
                    tag == tagCheckBoxList[it]
                }?.isChecked = true
            }
            review.imgs?.forEach {
                viewModel.addImagesOfCafe(PictureUriEntity(null, it))
            }
            viewModel.changeInitialImageCounts(review.imgs?.count() ?: 0)
            viewModel.changeCheckedRecommend(review.recommend)
            viewModel.contentsOfReview.value = review.content
            viewModel.changeReviewId(review._id)
            viewModel.rateOfReview.value = review.rating.toFloat()
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
                createDialogButton("앨범에서 선택") {
                    checkGalleryPermissions()
                    dialog.dismiss()
                }
            )
            add(
                createDialogButton("카메라 촬영") {
                    checkCameraPermissions()
                    dialog.dismiss()
                }
            )
        }

        binding.buttonAddPicture.root.setOnClickListener {
            dialog.show(supportFragmentManager, "picture")
        }
    }

    private fun createDialogButton(text: String, clickButton: () -> Unit): CapinDialogButton {
        return CapinDialogButton(
            text,
            ContextCompat.getColor(this@WriteReviewActivity, R.color.pointcolor_1),
            this@WriteReviewActivity,
            object : CapinDialogButton.OnClickListener {
                override fun onClick() {
                    if (viewModel.checkImagesCount()) {
                        clickButton()
                    } else {
                        createCapinRejectToast(this@WriteReviewActivity, "최대 5장까지 추가가능합니다.", 200)?.show()
                    }
                }
            })
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
        if (ContextCompat.checkSelfPermission(
                this,
                PERMISSION_READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestGalleryPermission.launch(PERMISSION_READ_EXTERNAL_STORAGE)
        } else {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            intent.type = "image/*"
            getHousePicture.launch(intent)
        }
    }

    private val requestCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                takePicture()
            }
        }

    private val requestGalleryPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
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

    private val cameraActivityLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSaved ->
            if (isSaved) {
                viewModel.addImagesOfCafe(PictureUriEntity(pictureUri, null))
            }
        }

    private val getHousePicture =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.data != null) {
                viewModel.addImagesOfCafe(PictureUriEntity(result.data?.data, null))
            }
        }

    private fun stagePictures() {
        binding.recyclerviewPicture.run {
            isNestedScrollingEnabled = false
            adapter = WriteReviewPictureAdapter(object : WriteReviewPictureAdapter.DeleteListener {
                override fun delete(pictureUriEntity: PictureUriEntity) {
                    viewModel.deleteImageOfCafe(pictureUriEntity)
                }
            })
            addItemDecoration(HorizontalItemDecoration(5))
            viewModel.imagesOfCafe.observe(this@WriteReviewActivity) {
                (adapter as WriteReviewPictureAdapter).apply {
                    submitList(it)
                    notifyDataSetChanged()
                }
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
            val checkedTags = mutableListOf<Int?>()
            tagCheckBoxList.forEach {
                if (it.key.isChecked) checkedTags.add(it.value)
            }
            viewModel.changeCheckedRecommend(checkedTags)
            if(intent.hasExtra(EDIT_REVIEW)) {
                viewModel.modifyReview(contentResolver)
            } else {
                viewModel.uploadReview(contentResolver)
            }
        }
    }

    private fun goToMapView() {
        viewModel.successPost.observe(this) { success ->
            when(success.status) {
                UiState.Status.LOADING -> {
                    binding.progressbar.visibility = View.VISIBLE
                }
                UiState.Status.SUCCESS -> {
                    binding.progressbar.visibility = View.GONE
                    finish()
                }
                UiState.Status.ERROR -> {
                    binding.progressbar.visibility = View.GONE
                    finish()
                }
            }
        }
    }

    private fun hideKeyBoard() {
        binding.constraintlayoutPictures.run {
            setOnClickListener { this.hideKeyBoard( ) }
        }
    }

    companion object {
        const val EDIT_REVIEW = "edit_review"
        private const val PERMISSION_CAMERA = android.Manifest.permission.CAMERA
        private const val PERMISSION_WRITE_EXTERNAL_STORAGE =
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        private const val PERMISSION_READ_EXTERNAL_STORAGE =
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        private val TAKE_PICTURE_PERMISSIONS = arrayOf(
            PERMISSION_CAMERA,
            PERMISSION_WRITE_EXTERNAL_STORAGE
        )

        const val KEY_CAFE_ID = "KEY_CAFE_ID"

    }
}