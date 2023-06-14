package com.alican.mvvm_starter.ui.detail.tabfragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.base.BaseFragment
import com.alican.mvvm_starter.databinding.FragmentMovieDetailCommentsBinding
import com.alican.mvvm_starter.ui.detail.MovieDetailViewModel
import com.alican.mvvm_starter.ui.detail.adapter.MovieReviewsPagingAdapter
import com.alican.mvvm_starter.ui.list.adapter.ListMoviesPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieReviewsFragment:BaseFragment<FragmentMovieDetailCommentsBinding>() {



    private val viewModel : MovieDetailViewModel by activityViewModels()

    private lateinit var adapter: MovieReviewsPagingAdapter
    override fun getLayoutId(): Int = R.layout.fragment_movie_detail_comments



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObserver()
    }

    private fun initViews() {
        adapter = MovieReviewsPagingAdapter {
        }
        binding.rvReviewsList.adapter = adapter
    }


    private fun initObserver() {
        lifecycleScope.launch{
            viewModel.reviews.collectLatest {
                adapter.submitData(it)
            }
        }

    }
}