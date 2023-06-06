/*
 * Created by Alican Sekban on 24.03.2023 16:07
 * Copyright © 2023 Alican Sekban. All rights reserved.
 */
package com.alican.mvvm_starter.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.util.utils.SPPARAM.NETWORK_CONNECTION
import com.blankj.utilcode.util.SPUtils
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.alican.mvvm_starter.ui.InternetConnectionActivity
import com.alican.mvvm_starter.ui.MainActivity
import com.alican.mvvm_starter.util.utils.customview.CustomDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class BaseActivity<VDB : ViewDataBinding> : AppCompatActivity() {

    lateinit var binding: VDB
    private lateinit var mCustomDialog: CustomDialog
    lateinit var bottomSheetDialog: com.alican.mvvm_starter.util.utils.BottomSheetDialog
    var isCheckInternetActive = true
    private var connectivityDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCustomDialog = CustomDialog(this, R.style.LoadingDialogStyle)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        checkInternetConnection()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    @Suppress("SameParameterValue")
    fun showBottomSheet(
        context: Context,
        title: Int,
        listener: com.alican.mvvm_starter.util.utils.BottomSheetDialog.BottomSheetListener,
    ) {
        bottomSheetDialog = com.alican.mvvm_starter.util.utils.BottomSheetDialog.instance.apply {
            setupSheet(context.getString(title))
            this.listener = listener
        }.also {
            it.show(supportFragmentManager, getString(title))
        }
    }

    fun showProgressDialog() {
        try {
            if (!mCustomDialog!!.isShowing) {
                mCustomDialog!!.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideProgressDialog() {
        try {
            if (mCustomDialog!!.isShowing)
                mCustomDialog!!.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Check Connection exist or not with reactive way
     */
    private fun checkInternetConnection() {
        connectivityDisposable = ReactiveNetwork
            .observeNetworkConnectivity(this)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { connectivity ->
                if (connectivity.available()) {
                    (this as? InternetConnectionActivity)?.finish()
                    if (SPUtils.getInstance().getBoolean(NETWORK_CONNECTION, false)) {
                        SPUtils.getInstance().put(NETWORK_CONNECTION, false)
                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                } else {
                    if (isCheckInternetActive) {
                        startActivity(Intent(this, InternetConnectionActivity::class.java))
                    }
                }
            }
    }

    /**
     * Dispose disposables when screen destroyed
     */
    override fun onDestroy() {
        super.onDestroy()
        connectivityDisposable?.let { if (!it.isDisposed) it.dispose() }
    }

}