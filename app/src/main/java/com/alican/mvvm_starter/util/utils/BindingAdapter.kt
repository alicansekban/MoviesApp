

package com.alican.mvvm_starter.util.utils

import android.widget.ImageView
import androidx.core.content.ContextCompat
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
            view.strokeColor = ContextCompat.getColor(view.context,color)
        }
    }
}