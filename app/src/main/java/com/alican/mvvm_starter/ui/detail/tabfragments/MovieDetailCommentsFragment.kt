package com.alican.mvvm_starter.ui.detail.tabfragments

import androidx.fragment.app.activityViewModels
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.base.BaseFragment
import com.alican.mvvm_starter.databinding.FragmentMovieDetailCommentsBinding
import com.alican.mvvm_starter.ui.detail.MovieDetailViewModel

class MovieDetailCommentsFragment:BaseFragment<FragmentMovieDetailCommentsBinding>() {


    private val viewModel : MovieDetailViewModel by activityViewModels()
    override fun getLayoutId(): Int = R.layout.fragment_movie_detail_comments
}