package com.alican.mvvm_starter.ui.detail.tabfragments

import android.os.Bundle
import android.view.View
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.base.BaseFragment
import com.alican.mvvm_starter.databinding.FragmentMoreLikeThisBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreLikeThisFragment:BaseFragment<FragmentMoreLikeThisBinding>() {
    override fun getLayoutId(): Int  = R.layout.fragment_more_like_this

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}