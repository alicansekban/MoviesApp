package com.alican.mvvm_starter.ui.detail

import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.base.BaseFragment
import com.alican.mvvm_starter.databinding.FragmentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment:BaseFragment<FragmentMovieDetailBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_movie_detail
}