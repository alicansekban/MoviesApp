package com.alican.mvvm_starter.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alican.mvvm_starter.databinding.ItemCastBinding
import com.alican.mvvm_starter.databinding.ItemMoviesBinding
import com.alican.mvvm_starter.domain.model.Cast
import com.alican.mvvm_starter.domain.model.MovieUIModel
import com.alican.mvvm_starter.ui.home.adapter.HomeMoviesAdapter

class MovieCastAdapter(val onClick: (Cast) -> Unit) : ListAdapter<Cast, MovieCastAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Cast>() {
    override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Cast,
        newItem: Cast
    ): Boolean {
        return oldItem == newItem
    }

}) {

    class ViewHolder(val binding: ItemCastBinding, onClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick(bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding) {
            onClick(getItem(it))
        }
    }

    override fun onBindViewHolder(holder: MovieCastAdapter.ViewHolder, position: Int) {
        holder.binding.data = getItem(position)
        holder.binding.executePendingBindings()
    }
}