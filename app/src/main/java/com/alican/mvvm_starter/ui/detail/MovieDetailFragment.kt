package com.alican.mvvm_starter.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.base.BaseFragment
import com.alican.mvvm_starter.databinding.FragmentMovieDetailBinding
import com.alican.mvvm_starter.domain.model.Cast
import com.alican.mvvm_starter.domain.model.Error
import com.alican.mvvm_starter.domain.model.Loading
import com.alican.mvvm_starter.domain.model.Success
import com.alican.mvvm_starter.ui.detail.adapter.MovieCastAdapter
import com.alican.mvvm_starter.ui.detail.adapter.MovieReviewsPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>() {

    private val viewModel by viewModels<MovieDetailViewModel>()
    private val args: MovieDetailFragmentArgs by navArgs()
    private lateinit var reviewsAdapter: MovieReviewsPagingAdapter

    override fun getLayoutId(): Int = R.layout.fragment_movie_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObserver()
        fetchData()
    }

    private fun initViews() {
        binding.rvCast.adapter = MovieCastAdapter {

        }
        reviewsAdapter = MovieReviewsPagingAdapter {
        }
        binding.rvReviews.adapter = reviewsAdapter

    }

    private fun fetchData() {
        viewModel.getMovieDetail(args.id)
        viewModel.getMovieCredits(args.id)
        viewModel.getMovieReviews(args.id)
    }

    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.movieDetail.collectLatest {
                when (it) {
                    is Error -> {}
                    is Loading -> {}
                    is Success -> binding.data = it.response
                }
            }
        }
        lifecycleScope.launch {
            viewModel.movieCredits.collectLatest {
                when (it) {
                    is Error -> {}
                    is Loading -> {}
                    is Success -> initCastAdapter(it.response.cast)
                }
            }
        }
        lifecycleScope.launch {
            viewModel.reviews.collectLatest {
                reviewsAdapter.submitData(it)
            }
        }

    }

    private fun initCastAdapter(cast: List<Cast>?) {
        (binding.rvCast.adapter as? MovieCastAdapter)?.submitList(cast?.map { it.copy() })

    }


}