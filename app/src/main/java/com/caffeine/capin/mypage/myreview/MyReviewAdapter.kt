package com.caffeine.capin.mypage.myreview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caffeine.capin.databinding.ItemMypageReviewBinding

class
MyReviewAdapter : RecyclerView.Adapter<MyReviewAdapter.MyReviewViewHolder>() {

    var myReviewList = mutableListOf<MyReview>()
    var mPosition = 0

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

    fun removeReview(position: Int) {
        if (position > 0) {
            myReviewList.removeAt(position)
            notifyDataSetChanged()
        }
    }

    fun removeChat() {
        myReviewList.removeAt(myReviewList.size-1)
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
        fun onBind(myReview: MyReview){
            binding.mypageReviewCafenameTv.text = myReview.cafeName
            binding.mypageReviewRatingTv.text = myReview.rating.toString()
            binding.mypageReviewContentsTv.text = myReview.content

            binding.mypageReviewEditBtn.setOnClickListener {
                editButtonClickListener.onEditButtonClick(myReview)
            }
        }
    }
}