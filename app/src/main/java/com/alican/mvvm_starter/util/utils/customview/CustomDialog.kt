package com.alican.mvvm_starter.util.utils.customview

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.airbnb.lottie.LottieAnimationView
import com.alican.mvvm_starter.R

class CustomDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {

    private var lottieView: LottieAnimationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.view_progress)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)
        lottieView = findViewById(R.id.lottieView)
        setIcon()
    }

    /**
     * Change Icon dynamically
     */
    private fun setIcon() {

    }
}