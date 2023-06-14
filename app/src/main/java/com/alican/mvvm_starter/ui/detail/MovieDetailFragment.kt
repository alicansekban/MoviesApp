package com.alican.mvvm_starter.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.base.BaseFragment
import com.alican.mvvm_starter.databinding.FragmentMovieDetailBinding
import com.alican.mvvm_starter.ui.detail.tabfragments.MoreLikeThisFragment
import com.alican.mvvm_starter.ui.detail.tabfragments.MovieDetailCommentsFragment
import com.alican.mvvm_starter.util.utils.GeneralPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>() {

    private val viewModel : MovieDetailViewModel by activityViewModels()
    override fun getLayoutId(): Int = R.layout.fragment_movie_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabs()
    }

    private fun initTabs() {
        val array = arrayOf(
            "More Like This",
            "Comments"
        )

        val viewPagerAdapter =
            GeneralPagerAdapter(requireActivity().supportFragmentManager, this, array)
        viewPagerAdapter.addFragment(MoreLikeThisFragment())
        viewPagerAdapter.addFragment(MovieDetailCommentsFragment())
        binding.viewPager.adapter = viewPagerAdapter

        val tabLayoutMediator =
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.text = array[position]
            }
        tabLayoutMediator.attach()
    }
}