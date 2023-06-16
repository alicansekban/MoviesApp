package com.alican.mvvm_starter.util.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.alican.mvvm_starter.R
import com.bumptech.glide.Glide

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("downloadImage")
    fun downloadImage(view: ImageView, url: String?) {
        if (url == null)
            return
        Glide.with(view.context)
            .load(url)
            .error(R.drawable.ic_launcher_background)
            .into(view)
    }

}