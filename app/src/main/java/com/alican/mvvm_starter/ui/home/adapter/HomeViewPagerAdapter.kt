package com.alican.mvvm_starter.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alican.mvvm_starter.databinding.ItemViewPagerMoviesBinding
import com.alican.mvvm_starter.domain.model.MovieUIModel

class HomeViewPagerAdapter(val onClick: (MovieUIModel) -> Unit) : ListAdapter<MovieUIModel, HomeViewPagerAdapter.ViewHolder>(object : DiffUtil.ItemCallback<MovieUIModel>() {
    override fun areItemsTheSame(oldItem: MovieUIModel, newItem: MovieUIModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: MovieUIModel,
        newItem: MovieUIModel
    ): Boolean {
        return oldItem == newItem
    }

}) {

    class ViewHolder(val binding: ItemViewPagerMoviesBinding, onClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick(bindingAdapterPosition)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemViewPagerMoviesBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding) {
            onClick(getItem(it))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.data = getItem(position)
        holder.binding.executePendingBindings()
    }
}