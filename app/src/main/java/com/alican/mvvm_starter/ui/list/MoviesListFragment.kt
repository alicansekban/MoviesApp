package com.alican.mvvm_starter.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.base.BaseFragment
import com.alican.mvvm_starter.databinding.FragmentMoviesListBinding
import com.alican.mvvm_starter.databinding.FragmentMoviesListBindingImpl
import com.alican.mvvm_starter.ui.home.HomeViewModel
import com.alican.mvvm_starter.ui.home.adapter.HomeMoviesPagingAdapter
import com.alican.mvvm_starter.ui.home.adapter.ListMoviesPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesListFragment:BaseFragment<FragmentMoviesListBinding>() {
    private val viewModel by viewModels<MoviesListViewModel>()
    override fun getLayoutId(): Int = R.layout.fragment_movies_list

    private lateinit var adapter: ListMoviesPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObserver()
    }

    private fun initViews() {
        adapter = ListMoviesPagingAdapter {
        }
        binding.rvMovieList.adapter = adapter
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.popularMovies.collectLatest {
                adapter.submitData(it)
            }
        }

    }
}