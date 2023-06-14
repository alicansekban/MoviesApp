package com.alican.mvvm_starter.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.viewpager2.widget.ViewPager2
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.databinding.FragmentHomeBinding
import com.alican.mvvm_starter.base.BaseFragment
import com.alican.mvvm_starter.data.local.model.MovieEntity
import com.alican.mvvm_starter.data.model.MovieModel
import com.alican.mvvm_starter.data.model.MovieResponseModel
import com.alican.mvvm_starter.domain.mapper.toMovieModel
import com.alican.mvvm_starter.domain.model.Error
import com.alican.mvvm_starter.domain.model.Loading
import com.alican.mvvm_starter.domain.model.MovieUIModel
import com.alican.mvvm_starter.domain.model.Success
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
        initViewPager()
    }

    private fun initViewPager() {
        binding.rvBanner.apply {
            offscreenPageLimit = 3
            setPageTransformer(SliderTransformer(3))
        }

    }




    private fun initViews() {
        adapter = HomeMoviesPagingAdapter {

        }
        binding.rvMovies.adapter = adapter
        binding.rvPopularMovies.adapter = HomeMoviesAdapter {
            goToDetailFragment(it.id)
        }
        binding.rvNowPlaying.adapter = HomeMoviesAdapter {
            goToListFragment(Constant.NOW_PLAYING)
        }
        binding.rvTopRatedMovies.adapter = HomeMoviesAdapter {
            goToListFragment(Constant.TOP_RATED_MOVIES)
        }

        binding.rvUpcomingMovies.adapter = HomeMoviesAdapter {
            goToListFragment(Constant.UP_COMING_MOVIES)

        }
        homeBannerAdapter = HomeViewPagerAdapter {
        }
        binding.rvBanner.adapter = homeBannerAdapter

    }

    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.movies.collect {
                adapter.submitData(it)
            }
        }
        lifecycleScope.launch {
            viewModel.popularMovies.collect { response ->
                when (response) {
                    is Error -> { response.errorMessage}
                    is Loading -> { showProgressDialog()}
                    is Success ->{

                        hideProgressDialog()
                        initPopularMoviesAdapter(response.response)
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.upComingMovies.collect { response ->
                when (response) {
                    is Error -> { response.errorMessage}
                    is Loading -> { showProgressDialog()}
                    is Success -> {
                        hideProgressDialog()
                        initUpComingMovies(response.response)
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.topRatedMovies.collect { response ->
                when (response) {
                    is Error -> { response.errorMessage}
                    is Loading -> { showProgressDialog()}
                    is Success ->{
                        hideProgressDialog()
                        initTopRatedAdapters(response.response)
                    }
                }
            }
        }
        lifecycleScope.launch{
            viewModel.nowPlayingMovies.collect { response ->
                when (response) {
                    is Error -> { response.errorMessage}
                    is Loading -> { showProgressDialog()}
                    is Success -> {
                        hideProgressDialog()
                        initNowPlayingMovies(response.response)
                    }
                }
            }

        }
    }

    private fun initNowPlayingMovies(list: List<MovieUIModel>) {
        (binding.rvNowPlaying.adapter as? HomeMoviesAdapter)?.submitList(list.map { it.copy() })
    }

    private fun initTopRatedAdapters(list: List<MovieUIModel>) {

        (binding.rvTopRatedMovies.adapter as? HomeMoviesAdapter)?.submitList(list.map { it.copy() })
    }

    private fun goToListFragment(type: String) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMoviesListFragment(type))
    }

    private fun goToDetailFragment(id:Int) {
       findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(id))
    }

    private fun initUpComingMovies(list: List<MovieUIModel>) {
        (binding.rvUpcomingMovies.adapter as? HomeMoviesAdapter)?.submitList(list.map { it.copy() })
    }

    private fun initPopularMoviesAdapter(list: List<MovieUIModel>) {
        (binding.rvPopularMovies.adapter as? HomeMoviesAdapter)?.submitList(list.map { it.copy() })
        homeBannerAdapter.submitList(list.map { it.copy() })

        binding.indicator.setViewPager2(binding.rvBanner)


    }
}
