package com.caffeine.capin.review.write.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.SystemClock
import android.transition.Transition
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.caffeine.capin.databinding.ItemReviewPictureBinding
import com.caffeine.capin.review.write.PictureUriEntity
import com.caffeine.capin.util.DiffCallback
import io.reactivex.rxjava3.core.Single
import java.io.File
import java.io.FileOutputStream
import java.util.*

class WriteReviewPictureAdapter(private val listener: DeleteListener) : ListAdapter<PictureUriEntity, WriteReviewPictureAdapter.WriteReviewPictureViewHolder>(
        DiffCallback<PictureUriEntity>()
    ) {
    interface DeleteListener{
        fun delete(pictureUriEntity: PictureUriEntity)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WriteReviewPictureViewHolder {
        val binding = ItemReviewPictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WriteReviewPictureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WriteReviewPictureViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.binding.buttonDelete.setOnClickListener {
            listener.delete(item)
        }
    }

    class WriteReviewPictureViewHolder(val binding: ItemReviewPictureBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PictureUriEntity) {
            binding.imageviewCafe.run {
                Glide.with(context).load(data.uri ?: data.url).into(this)
            }
        }
    }

}