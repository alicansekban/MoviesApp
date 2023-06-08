package com.alican.mvvm_starter.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.databinding.FragmentHomeBinding
import com.alican.mvvm_starter.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val viewModel by viewModels<HomeViewModel>()
    override fun getLayoutId(): Int = R.layout.fragment_home
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        lifecycleScope.launch {
            viewModel.movies.collect {
            }
        }

    }

}
