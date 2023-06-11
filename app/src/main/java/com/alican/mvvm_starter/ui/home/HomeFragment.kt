package com.alican.mvvm_starter.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.databinding.FragmentHomeBinding
import com.alican.mvvm_starter.base.BaseFragment
import com.alican.mvvm_starter.data.local.model.MovieEntity
import com.alican.mvvm_starter.data.model.MovieModel
import com.alican.mvvm_starter.data.model.MovieResponseModel
import com.alican.mvvm_starter.domain.mapper.toMovieModel
import com.alican.mvvm_starter.ui.home.adapter.HomeMoviesAdapter
import com.alican.mvvm_starter.ui.home.adapter.HomeMoviesPagingAdapter
import com.murgupluoglu.request.STATUS_ERROR
import com.murgupluoglu.request.STATUS_LOADING
import com.murgupluoglu.request.STATUS_SUCCESS
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.migration.CustomInjection.inject
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var adapter: HomeMoviesPagingAdapter


    override fun getLayoutId(): Int = R.layout.fragment_home
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

        viewModel.dataResponse.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                STATUS_LOADING -> {
                    showProgressDialog()
                }

                STATUS_ERROR -> {
                    hideProgressDialog()
                }

                STATUS_SUCCESS -> {
                    hideProgressDialog()
                    val result = response.responseObject
                    result?.let {
                        val items = result.results.map {
                            it.toMovieModel()
                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.movies.collect {
                adapter.submitData(it)
            }
        }
    }
}
