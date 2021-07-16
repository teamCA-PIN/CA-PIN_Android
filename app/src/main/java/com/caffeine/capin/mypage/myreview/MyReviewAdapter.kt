package com.caffeine.capin.mypage.myreview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.caffeine.capin.databinding.ItemMypageReviewBinding

class
MyReviewAdapter : RecyclerView.Adapter<MyReviewAdapter.MyReviewViewHolder>() {

    var myReviewList = mutableListOf<MyReview>()
    var mPosition = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyReviewAdapter.MyReviewViewHolder {
        val binding = ItemMypageReviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyReviewViewHolder(binding)
    }

    override fun getItemCount(): Int = myReviewList.size

    override fun onBindViewHolder(holder: MyReviewViewHolder, position: Int) {
        holder.onBind(myReviewList[position])
    }

    fun removeReview(position: Int) {
        if (position > 0) {
            myReviewList.removeAt(position)
            notifyDataSetChanged()
        }
    }

    fun removeChat() {
        myReviewList.removeAt(myReviewList.size - 1)
        notifyItemRemoved(myReviewList.size)
    }

    interface OnEditButtonClickListener {
        fun onEditButtonClick(myReview: MyReview)
    }

    private lateinit var editButtonClickListener: OnEditButtonClickListener

    fun setOnEditButtonClickListener(listener: OnEditButtonClickListener) {
        this.editButtonClickListener = listener
    }

    inner class MyReviewViewHolder(
        private val binding: ItemMypageReviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(myReview: MyReview) {
            binding.mypageReviewCafenameTv.text = myReview.cafeName
            binding.mypageReviewRatingTv.text = myReview.rating.toString()
            binding.mypageReviewContentsTv.text = myReview.content
            if (myReview.imgs != null) {
                when (myReview.imgs.size) {
                    1 -> {
                        Glide
                            .with(binding.mypageReviewImgsFirstIv.context)
                            .load(myReview.imgs[0])
                            .into(binding.mypageReviewImgsFirstIv)
                        binding.mypageReviewImgsSecondIv.isVisible = false
                        binding.mypageReviewImgsThirdIv.isVisible = false
                    }
                    2 -> {
                        Glide
                            .with(binding.mypageReviewImgsFirstIv.context)
                            .load(myReview.imgs[0])
                            .into(binding.mypageReviewImgsFirstIv)
                        Glide
                            .with(binding.mypageReviewImgsSecondIv.context)
                            .load(myReview.imgs[1])
                            .into(binding.mypageReviewImgsSecondIv)
                        binding.mypageReviewImgsThirdIv.isVisible = false
                    }
                    3 -> {
                        Glide
                            .with(binding.mypageReviewImgsFirstIv.context)
                            .load(myReview.imgs[0])
                            .into(binding.mypageReviewImgsFirstIv)
                        Glide
                            .with(binding.mypageReviewImgsSecondIv.context)
                            .load(myReview.imgs[1])
                            .into(binding.mypageReviewImgsSecondIv)
                        Glide
                            .with(binding.mypageReviewImgsThirdIv.context)
                            .load(myReview.imgs[2])
                            .into(binding.mypageReviewImgsThirdIv)
                    }
                    else -> {
                        Log.d("리미", "이미지다섯개왜안들어와")
                        Glide
                            .with(binding.mypageReviewImgsFirstIv.context)
                            .load(myReview.imgs[0])
                            .into(binding.mypageReviewImgsFirstIv)
                        Glide
                            .with(binding.mypageReviewImgsSecondIv.context)
                            .load(myReview.imgs[1])
                            .into(binding.mypageReviewImgsSecondIv)
                        Glide
                            .with(binding.mypageReviewImgsThirdIv.context)
                            .load(myReview.imgs[2])
                            .into(binding.mypageReviewImgsThirdIv)

                        binding.mypageReviewImgsSeemoreBtn.isVisible = true
                        binding.mypageReviewImgsSeemoreTv.isVisible = true
                        binding.mypageReviewImgsSeemoreTv.text = "+${myReview.imgs.size - 2}"
                    }
                }
            } else {
                binding.mypageReviewImgsFirstIv.isVisible = false
                binding.mypageReviewImgsSecondIv.isVisible = false
                binding.mypageReviewImgsThirdIv.isVisible = false
            }

            if (myReview.recommend != null) {
                when (myReview.recommend) {
                    listOf(0) -> {
                        binding.mypageReviewTypeMoodBtn.isVisible = false
                        binding.mypageReviewTypeFlavorBtn.setText("분위기 추천")
                    }
                    listOf(1) -> {
                        binding.mypageReviewTypeMoodBtn.isVisible = false
                    }
                }
            } else {
                binding.mypageReviewTypeFlavorBtn.isGone = true
                binding.mypageReviewTypeMoodBtn.isGone = true
            }

            binding.mypageReviewEditBtn.setOnClickListener {
                editButtonClickListener.onEditButtonClick(myReview)
            }
        }
    }
}