package com.alican.mvvm_starter.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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
import com.alican.mvvm_starter.ui.home.adapter.HomeViewPagerAdapter
import com.alican.mvvm_starter.util.Constant
import com.alican.mvvm_starter.util.utils.SliderTransformer
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
    private lateinit var homeBannerAdapter: HomeViewPagerAdapter


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
        binding.rvPopularMovies.adapter = HomeMoviesAdapter {
            goToListFragment(Constant.POPULAR_MOVIES)
        }

        binding.rvUpcomingMovies.adapter = HomeMoviesAdapter {
            goToListFragment(Constant.UP_COMING_MOVIES)

        }
        homeBannerAdapter = HomeViewPagerAdapter {
        }
        binding.rvBanner.adapter = homeBannerAdapter
    }

    private fun initObserver() {
        viewModel.progressLiveData.observe(viewLifecycleOwner) {
            if (it) showProgressDialog() else hideProgressDialog()
        }
        lifecycleScope.launch {
            viewModel.movies.collect {
                adapter.submitData(it)
            }
        }
        lifecycleScope.launch {
            viewModel.popularMovies.collect { list ->
                initAdapter(list)
            }
        }
        lifecycleScope.launch {
            viewModel.upComingMovies.collect { list ->
                initUpComingMovies(list)
            }
        }
    }
    private fun goToListFragment(type: String) {
        val action = findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMoviesListFragment(type))
    }

    private fun initUpComingMovies(list: List<MovieModel>) {
        (binding.rvUpcomingMovies.adapter as? HomeMoviesAdapter)?.submitList(list.map { it.copy() })
    }

    private fun initAdapter(list: List<MovieModel>) {
        (binding.rvPopularMovies.adapter as? HomeMoviesAdapter)?.submitList(list.map { it.copy() })
        homeBannerAdapter.submitList(list.map { it.copy() })


    }
}
