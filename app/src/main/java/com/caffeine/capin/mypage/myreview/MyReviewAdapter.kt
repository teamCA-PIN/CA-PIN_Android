package com.caffeine.capin.mypage.myreview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caffeine.capin.databinding.ItemMypageReviewBinding

class MyReviewAdapter : RecyclerView.Adapter<MyReviewAdapter.MyReviewViewHolder>() {

    val myReviewList = mutableListOf<MyReview>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewAdapter.MyReviewViewHolder {
        val binding = ItemMypageReviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyReviewViewHolder(binding)
    }
    override fun getItemCount(): Int  = myReviewList.size

    override fun onBindViewHolder(holder: MyReviewViewHolder, position: Int) {
        holder.onBind(myReviewList[position])
    }

    class MyReviewViewHolder(
        private val binding: ItemMypageReviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(myReview: MyReview){
            binding.mypageReviewCafenameTv.text = myReview.cafeName
            binding.mypageReviewRatingTv.text = myReview.rating.toString()
            binding.mypageReviewContentsTv.text = myReview.content
        }
    }
}