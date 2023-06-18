package com.alican.mvvm_starter.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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
        initToolbar()
        initViews()
        initObserver()
        fetchData()
    }

    private fun initToolbar() {
        binding.toolBar.clBack.visibility = View.VISIBLE
        binding.toolBar.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolBar.tvTitle.text = getString(R.string.txt_movie_detail_title)
    }

        private fun initViews() {
        binding.rvCast.adapter = MovieCastAdapter {

        }
        reviewsAdapter = MovieReviewsPagingAdapter()
        val itemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
     //   binding.rvReviews.addItemDecoration(itemDecoration)
        binding.rvReviews.adapter = reviewsAdapter

    }

    private fun fetchData() {
        viewModel.getMovieDetail(args.id)
        viewModel.getMovieCredits(args.id)
        viewModel.getReviews(args.id)
    }

    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.movieDetail.collectLatest {
                when (it) {
                    is Error -> { Toast.makeText(requireContext(),it.errorMessage, Toast.LENGTH_LONG).show()}
                    is Loading -> {showProgressDialog()}
                    is Success -> binding.data = it.response
                }
            }
        }
        lifecycleScope.launch {
            viewModel.movieCredits.collectLatest {
                when (it) {
                    is Error -> { Toast.makeText(requireContext(),it.errorMessage, Toast.LENGTH_LONG).show()}
                    is Loading -> {showProgressDialog()}
                    is Success -> {
                        hideProgressDialog()
                        initCastAdapter(it.response.cast)
                    }
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