
package com.alican.mvvm_starter.util.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.alican.mvvm_starter.R
import com.bumptech.glide.Glide


fun ImageView.loadImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        .error(R.drawable.ic_launcher_foreground)
        .into(this)
}