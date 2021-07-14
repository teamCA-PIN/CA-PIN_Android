package com.caffeine.capin.detail.menus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.caffeine.capin.databinding.ItemCafeMenuBinding

/**
 * Created By Malibin
 * on 7월 14, 2021
 */

class CafeMenusAdapter : ListAdapter<CafeMenu, CafeMenusAdapter.ViewHolder>(ItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCafeMenuBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemCafeMenuBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cafeMenu: CafeMenu) {
            binding.menu = cafeMenu
        }
    }

    private class ItemComparator : DiffUtil.ItemCallback<CafeMenu>() {
        override fun areItemsTheSame(oldItem: CafeMenu, newItem: CafeMenu): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CafeMenu, newItem: CafeMenu): Boolean {
            return oldItem == newItem
        }
    }
}
