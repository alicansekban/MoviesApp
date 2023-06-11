package com.alican.mvvm_starter.util.utils

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.alican.mvvm_starter.R
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView

object BindingAdapter {
    @JvmStatic
    @androidx.databinding.BindingAdapter("setDrawableResource")
    fun setDrawableResource(view: ImageView, drawable: Int?) {
        if (drawable == null)
            return
        view.setImageResource(drawable)
    }

    @JvmStatic
    @androidx.databinding.BindingAdapter("app:setCardBackground")
    fun setCardBackgroundColor(view: MaterialCardView, color: Int?) {
        if (color != null && color > 0) {
            view.setCardBackgroundColor(ContextCompat.getColor(view.context, color))
        }
    }

    @JvmStatic
    @androidx.databinding.BindingAdapter("app:setCardStrokeColor")
    fun setCardStrokeColor(view: MaterialCardView, color: Int?) {
        color?.let {
            view.strokeColor = ContextCompat.getColor(view.context, color)
        }
    }

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