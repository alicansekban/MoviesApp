package com.alican.mvvm_starter.ui.list

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.base.BaseFragment
import com.alican.mvvm_starter.databinding.FragmentMoviesListBinding
import com.alican.mvvm_starter.ui.list.adapter.ListMoviesPagingAdapter
import com.alican.mvvm_starter.util.Constant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesListFragment : BaseFragment<FragmentMoviesListBinding>() {
    private val viewModel by viewModels<MoviesListViewModel>()
    private val args by navArgs<MoviesListFragmentArgs>()
    override fun getLayoutId(): Int = R.layout.fragment_movies_list

    private lateinit var adapter: ListMoviesPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initViews()
        getData()
        initObserver()
        initSearch()
    }

    private fun initToolbar() {
        binding.toolBar.clBack.visibility = View.VISIBLE
        binding.toolBar.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolBar.tvTitle.text = args.title
    }

    private fun initSearch() {
        binding.edtSearch.addTextChangedListener {
            if (it?.length!! > 2) {
                viewModel.searchMovies(query = it.toString())
            } else {
                getData()
            }
        }
    }

    private fun getData() {
        when (args.type) {
            Constant.POPULAR_MOVIES -> {
                viewModel.getPopularMovies()
            }

            Constant.UP_COMING_MOVIES -> {
                viewModel.getUpComingMovies()
            }

            Constant.TOP_RATED_MOVIES -> {
                viewModel.getTopRatedMovies()
            }

            Constant.NOW_PLAYING -> {
                viewModel.getNowPlaying()
            }
        }
    }

    private fun initViews() {
        adapter = ListMoviesPagingAdapter {
            findNavController().navigate(MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailFragment(it.id))
        }
        binding.rvMovieList.adapter = adapter
    }

    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.movies.collectLatest { response ->
                adapter.submitData(response)
            }
            adapter.loadStateFlow.collectLatest { loadStates ->
                if (loadStates.refresh is LoadState.Loading) showProgressDialog() else hideProgressDialog()
            }
        }


    }
}