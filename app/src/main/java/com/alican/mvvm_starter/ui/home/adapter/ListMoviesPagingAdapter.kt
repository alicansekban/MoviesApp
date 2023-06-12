package com.alican.mvvm_starter.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alican.mvvm_starter.data.local.model.MovieEntity
import com.alican.mvvm_starter.data.model.MovieModel
import com.alican.mvvm_starter.databinding.ItemHomeMoviesBinding
import com.alican.mvvm_starter.databinding.ItemMoviesListBinding
import com.alican.mvvm_starter.util.utils.loadImage

class ListMoviesPagingAdapter(private val onItemClick: (MovieModel) -> Unit) : PagingDataAdapter<MovieModel, ListMoviesPagingAdapter.ListMoviesViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMoviesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMoviesListBinding.inflate(inflater, parent, false)
        return ListMoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListMoviesViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class ListMoviesViewHolder(private val binding: ItemMoviesListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieModel) {
            binding.root.setOnClickListener {
                onItemClick(item)
            }
            item.let {
                binding.tvTitle.text = it.title
                binding.ivCatalog.loadImage(it.getImagePath())
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieModel>() {
            override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
