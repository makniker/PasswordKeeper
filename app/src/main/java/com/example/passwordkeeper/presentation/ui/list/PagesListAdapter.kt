package com.example.passwordkeeper.presentation.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.passwordkeeper.R
import com.example.passwordkeeper.databinding.ItemPageBinding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class PagesListAdapter(private val listener: (PageListUI) -> Unit) :
    ListAdapter<PageListUI, PagesListAdapter.PageViewHolder>(DIFF_UTIL) {

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<PageListUI>() {
            override fun areItemsTheSame(oldItem: PageListUI, newItem: PageListUI): Boolean =
                oldItem.url == newItem.url

            override fun areContentsTheSame(oldItem: PageListUI, newItem: PageListUI): Boolean =
                oldItem == newItem
        }
    }

    inner class PageViewHolder(private val binding: ItemPageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PageListUI) {
            binding.title.text = item.title
            if (item.iconProvided) {
                Glide.with(binding.pageImage)
                    .load(item.url)
                    .diskCacheStrategy(DiskCacheStrategy.DATA).error(R.drawable.ic_no_photo)
                    .into(binding.pageImage)
            } else {
                Glide.with(binding.pageImage).load(R.drawable.ic_no_photo).into(binding.pageImage)
            }
            itemView.setOnClickListener { listener(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder =
        PageViewHolder(
            ItemPageBinding.inflate(LayoutInflater.from(parent.context))
        )

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}