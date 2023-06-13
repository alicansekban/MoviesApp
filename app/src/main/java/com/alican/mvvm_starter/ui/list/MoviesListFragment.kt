package com.alican.mvvm_starter.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.base.BaseFragment
import com.alican.mvvm_starter.databinding.FragmentMoviesListBinding
import com.alican.mvvm_starter.databinding.FragmentMoviesListBindingImpl
import com.alican.mvvm_starter.ui.home.HomeViewModel
import com.alican.mvvm_starter.ui.home.adapter.HomeMoviesPagingAdapter
import com.alican.mvvm_starter.ui.home.adapter.ListMoviesPagingAdapter
import com.alican.mvvm_starter.util.Constant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesListFragment:BaseFragment<FragmentMoviesListBinding>() {
    private val viewModel by viewModels<MoviesListViewModel>()
    private val args by navArgs<MoviesListFragmentArgs>()
    override fun getLayoutId(): Int = R.layout.fragment_movies_list

    private lateinit var adapter: ListMoviesPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeArgs()
        initObserver()
    }

    private fun observeArgs() {
        when(args.type) {
            Constant.POPULAR_MOVIES -> {
                viewModel._movies = viewModel.interactor.getUpcomingMovies()
            }
            Constant.UP_COMING_MOVIES -> {
                viewModel._movies = viewModel.interactor.getUpcomingMovies()
            }
            Constant.TOP_RATED_MOVIES -> {
                viewModel._movies = viewModel.interactor.getTopRatedMovies()
            }
        }
    }

    private fun initViews() {
        adapter = ListMoviesPagingAdapter {
        }
        binding.rvMovieList.adapter = adapter
    }

    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.movies.collectLatest { response ->
                adapter.submitData(response)
            }
        }

    }
}