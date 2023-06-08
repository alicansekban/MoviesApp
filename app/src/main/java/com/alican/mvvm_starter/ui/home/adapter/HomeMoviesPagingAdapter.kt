package com.alican.mvvm_starter.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alican.mvvm_starter.data.local.model.MovieEntity
import com.alican.mvvm_starter.databinding.ItemHomeMoviesBinding

class HomeMoviesPagingAdapter(private val onItemClick: (MovieEntity) -> Unit) : PagingDataAdapter<MovieEntity, HomeMoviesPagingAdapter.ExampleViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHomeMoviesBinding.inflate(inflater, parent, false)
        return ExampleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class ExampleViewHolder(private val binding: ItemHomeMoviesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieEntity) {
            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}
