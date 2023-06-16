package com.alican.mvvm_starter.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.base.BaseFragment
import com.alican.mvvm_starter.databinding.FragmentHomeBinding
import com.alican.mvvm_starter.domain.model.Error
import com.alican.mvvm_starter.domain.model.Loading
import com.alican.mvvm_starter.domain.model.MovieUIModel
import com.alican.mvvm_starter.domain.model.Success
import com.alican.mvvm_starter.ui.home.adapter.HomeMoviesAdapter
import com.alican.mvvm_starter.ui.home.adapter.HomeViewPagerAdapter
import com.alican.mvvm_starter.util.Constant
import com.alican.mvvm_starter.util.utils.SliderTransformer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var homeBannerAdapter: HomeViewPagerAdapter


    override fun getLayoutId(): Int = R.layout.fragment_home
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObserver()
        initViewPager()
        setListeners()
    }

    private fun setListeners() {
        binding.btnAllPopularMovies.setOnClickListener {
            goToListFragment(Constant.POPULAR_MOVIES)
        }
        binding.btnAllNowPlayingMovies.setOnClickListener {
            goToListFragment(Constant.NOW_PLAYING)
        }
        binding.btnAllTopRatedMovies.setOnClickListener {
            goToListFragment(Constant.TOP_RATED_MOVIES)
        }
        binding.btnAllUpcomingMovies.setOnClickListener {
            goToListFragment(Constant.UP_COMING_MOVIES)
        }
    }

    private fun initViewPager() {
        binding.rvBanner.apply {
            offscreenPageLimit = 3
            setPageTransformer(SliderTransformer(3))
        }

    }


    private fun initViews() {

        binding.rvPopularMovies.adapter = HomeMoviesAdapter {
            goToDetailFragment(it.id)
        }
        binding.rvNowPlaying.adapter = HomeMoviesAdapter {
            goToDetailFragment(it.id)
        }
        binding.rvTopRatedMovies.adapter = HomeMoviesAdapter {
            goToDetailFragment(it.id)
        }

        binding.rvUpcomingMovies.adapter = HomeMoviesAdapter {
            goToDetailFragment(it.id)

        }
        homeBannerAdapter = HomeViewPagerAdapter {
        }
        binding.rvBanner.adapter = homeBannerAdapter

    }

    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.popularMovies.collect { response ->
                when (response) {
                    is Error -> {
                        response.errorMessage
                    }

                    is Loading -> {
                        showProgressDialog()
                    }

                    is Success -> {

                        hideProgressDialog()
                        initPopularMoviesAdapter(response.response)
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.upComingMovies.collect { response ->
                when (response) {
                    is Error -> {
                        response.errorMessage
                    }

                    is Loading -> {
                        showProgressDialog()
                    }

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
                    is Error -> {
                        response.errorMessage
                    }

                    is Loading -> {
                        showProgressDialog()
                    }

                    is Success -> {
                        hideProgressDialog()
                        initTopRatedAdapters(response.response)
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.nowPlayingMovies.collect { response ->
                when (response) {
                    is Error -> {
                        response.errorMessage
                    }

                    is Loading -> {
                        showProgressDialog()
                    }

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
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToMoviesListFragment(
                type
            )
        )
    }

    private fun goToDetailFragment(id: Int) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(
                id
            )
        )
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
