package com.alican.mvvm_starter.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alican.mvvm_starter.databinding.ItemMoviesListBinding
import com.alican.mvvm_starter.databinding.ItemReviewBinding
import com.alican.mvvm_starter.domain.model.Cast
import com.alican.mvvm_starter.domain.model.MovieDetailReviewsUIModel
import com.alican.mvvm_starter.domain.model.MovieUIModel
import com.alican.mvvm_starter.util.utils.loadImage

class MovieReviewsPagingAdapter(private val onItemClick: (MovieDetailReviewsUIModel) -> Unit) : PagingDataAdapter<MovieDetailReviewsUIModel, MovieReviewsPagingAdapter.MovieReviewsViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieReviewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemReviewBinding.inflate(inflater, parent, false)
        return MovieReviewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieReviewsViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class MovieReviewsViewHolder(private val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieDetailReviewsUIModel) {
            binding.root.setOnClickListener {
                onItemClick(item)
            }
            item.let {
                binding.tvTitle.text = it.authorDetails?.name
                binding.tvDesc.text = it.content
                binding.ivProfile.loadImage(it.authorDetails?.getImage())
            }

        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieDetailReviewsUIModel>() {
            override fun areItemsTheSame(oldItem: MovieDetailReviewsUIModel, newItem: MovieDetailReviewsUIModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieDetailReviewsUIModel, newItem: MovieDetailReviewsUIModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
