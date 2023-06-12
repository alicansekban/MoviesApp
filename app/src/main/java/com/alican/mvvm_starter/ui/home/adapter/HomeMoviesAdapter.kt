package com.alican.mvvm_starter.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alican.mvvm_starter.data.model.MovieModel
import com.alican.mvvm_starter.databinding.ItemHomeMoviesBinding
import com.alican.mvvm_starter.databinding.ItemMoviesBinding

class HomeMoviesAdapter(val onClick: (MovieModel) -> Unit) : ListAdapter<MovieModel, HomeMoviesAdapter.ViewHolder>(object : DiffUtil.ItemCallback<MovieModel>() {
    override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: MovieModel,
        newItem: MovieModel
    ): Boolean {
        return oldItem == newItem
    }

}) {

    class ViewHolder(val binding: ItemMoviesBinding, onClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick(bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding) {
            onClick(getItem(it))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.data = getItem(position)
        holder.binding.executePendingBindings()
    }
}

