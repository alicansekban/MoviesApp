package com.alican.mvvm_starter.ui

import android.os.Bundle
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.base.BaseActivity
import com.alican.mvvm_starter.databinding.ActivityInternetConnectionBinding
import com.blankj.utilcode.util.SPUtils
import com.alican.mvvm_starter.util.utils.SPPARAM

class InternetConnectionActivity : BaseActivity<ActivityInternetConnectionBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_internet_connection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internet_connection)
        isCheckInternetActive = false
        SPUtils.getInstance().put(SPPARAM.NETWORK_CONNECTION, true)
    }

    override fun onBackPressed() {
    }

}