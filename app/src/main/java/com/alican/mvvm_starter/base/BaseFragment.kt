
package com.alican.mvvm_starter.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.util.utils.BottomSheetDialog
import com.blankj.utilcode.util.LogUtils
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

abstract class BaseFragment<VDB : ViewDataBinding> : Fragment() {

    lateinit var binding: VDB
    private var dialog: ProgressDialog? = null
    private var wheelingProcessDialogScheduler: ScheduledExecutorService? = null
    lateinit var bottomSheetDialog: BottomSheetDialog

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

    private fun startWheelingProcessDialogScheduler(d: ProgressDialog?) {
        if (wheelingProcessDialogScheduler != null && !wheelingProcessDialogScheduler!!.isShutdown) {
            stopWheelingProcessDialogScheduler()
        }
        wheelingProcessDialogScheduler = Executors.newSingleThreadScheduledExecutor()
        wheelingProcessDialogScheduler!!.schedule({
            requireActivity().runOnUiThread {
                try {
                    if (d != null && d.isShowing) {
                        d.dismiss()
                    }
                } catch (e: Throwable) {
                    LogUtils.e("Progress dialog scheduler error ", e)
                }
            }
        }, 20.toLong(), TimeUnit.SECONDS)
    }

    private fun stopWheelingProcessDialogScheduler() {
        try {
            if (wheelingProcessDialogScheduler != null) {
                wheelingProcessDialogScheduler!!.shutdownNow()
                wheelingProcessDialogScheduler = null
            }
        } catch (e: Throwable) {
            LogUtils.e(e.message, e)
        }
    }

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

    @Suppress("SameParameterValue")
    fun showBottomSheet(
        context: Context,
        title: Int,
        listener: BottomSheetDialog.BottomSheetListener,
    ) {
        bottomSheetDialog = BottomSheetDialog.instance.apply {
            setupSheet(context.getString(title))
            this.listener = listener
        }.also {
            it.show(childFragmentManager, getString(title))
        }
    }
}