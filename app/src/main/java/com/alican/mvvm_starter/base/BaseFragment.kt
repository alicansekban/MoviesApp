
package com.alican.mvvm_starter.base

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.alican.mvvm_starter.R

abstract class BaseFragment<VDB : ViewDataBinding> : Fragment() {

    lateinit var binding: VDB
    private var dialog: ProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog = context?.let { ProgressDialog(it, R.style.LoadingDialogStyle) }!!
        binding = DataBindingUtil.inflate(layoutInflater, getLayoutId(), container, false)
        return binding.root
    }

    @LayoutRes
    abstract fun getLayoutId(): Int



    /**
     * Show Progress Dialog
     */
    fun showProgressDialog() {
        val activity = activity as BaseActivity<*>
        activity.showProgressDialog()
    }

    /**
     * Hide Progress Dialog
     */
    fun hideProgressDialog() {
        val activity = activity as BaseActivity<*>
        activity.hideProgressDialog()
    }
}