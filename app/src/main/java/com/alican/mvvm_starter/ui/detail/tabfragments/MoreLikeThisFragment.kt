package com.alican.mvvm_starter.ui.detail.tabfragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.base.BaseFragment
import com.alican.mvvm_starter.databinding.FragmentMoreLikeThisBinding
import com.alican.mvvm_starter.ui.detail.MovieDetailViewModel
import com.alican.mvvm_starter.ui.home.adapter.HomeMoviesPagingAdapter
import com.alican.mvvm_starter.ui.list.MoviesListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoreLikeThisFragment : BaseFragment<FragmentMoreLikeThisBinding>() {


    private val viewModel by viewModels <MovieDetailViewModel>()
    override fun getLayoutId(): Int = R.layout.fragment_more_like_this

    private lateinit var adapter: HomeMoviesPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObserver()
    }


    private fun initViews() {

        adapter = HomeMoviesPagingAdapter {

        }
        binding.rvMovies.adapter = adapter
    }

    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.movies.collect {
                adapter.submitData(it)
            }
        }
    }
}