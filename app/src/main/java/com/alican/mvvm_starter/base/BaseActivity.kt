/*
 * Created by Alican Sekban on 24.03.2023 16:07
 * Copyright © 2023 Alican Sekban. All rights reserved.
 */
package com.alican.mvvm_starter.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.util.utils.customview.CustomDialog

abstract class BaseActivity<VDB : ViewDataBinding> : AppCompatActivity() {

    lateinit var binding: VDB
    private lateinit var mCustomDialog: CustomDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCustomDialog = CustomDialog(this, R.style.LoadingDialogStyle)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
    }

    @LayoutRes
    abstract fun getLayoutId(): Int


    fun showProgressDialog() {
        try {
            if (!mCustomDialog.isShowing) {
                mCustomDialog.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideProgressDialog() {
        try {
            if (mCustomDialog.isShowing)
                mCustomDialog.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Check Connection exist or not with reactive way
     */


    /**
     * Dispose disposables when screen destroyed
     */

}