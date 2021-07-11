package com.caffeine.capin.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.caffeine.capin.databinding.ItemReviewPictureBinding

class WriteReviewPictureAdapter(private val listener: DeleteListener) : ListAdapter<PictureUriEntity, WriteReviewPictureAdapter.WriteReviewPictureViewHolder>(
        diffCallback
    ) {
    interface DeleteListener{
        fun delete(pictureUriEntity: PictureUriEntity)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WriteReviewPictureViewHolder {
        val binding =
            ItemReviewPictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WriteReviewPictureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WriteReviewPictureViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.setVariable(BR.data, item)
        holder.binding.buttonDelete.setOnClickListener {
            listener.delete(item)
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<PictureUriEntity>() {
            override fun areItemsTheSame(
                oldItem: PictureUriEntity,
                newItem: PictureUriEntity
            ): Boolean {
                return oldItem.uri == newItem.uri
            }

            override fun areContentsTheSame(
                oldItem: PictureUriEntity,
                newItem: PictureUriEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    class WriteReviewPictureViewHolder(val binding: ItemReviewPictureBinding) : RecyclerView.ViewHolder(binding.root)
}