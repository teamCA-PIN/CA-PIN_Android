package com.caffeine.capin.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.caffeine.capin.databinding.ItemReviewPictureBinding
import com.caffeine.capin.databinding.ItemWriteReviewAddPictureBinding

class WriteReviewPictureAdapter: ListAdapter<PictureUriEntity,WriteReviewPictureAdapter.WriteReviewPictureViewHolder>(
    diffCallback
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WriteReviewPictureViewHolder {
        val binding = ItemReviewPictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WriteReviewPictureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WriteReviewPictureViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.setVariable(BR.data, item)
    }

    override fun getItemCount() = currentList.size

    companion object {
        val diffCallback = object: DiffUtil.ItemCallback<PictureUriEntity>() {
            override fun areItemsTheSame(
                oldItem: PictureUriEntity,
                newItem: PictureUriEntity
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(
                oldItem: PictureUriEntity,
                newItem: PictureUriEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    class WriteReviewPictureViewHolder(val binding: ItemReviewPictureBinding): RecyclerView.ViewHolder(binding.root)
}