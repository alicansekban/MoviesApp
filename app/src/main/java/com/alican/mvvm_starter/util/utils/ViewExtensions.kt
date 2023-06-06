
package com.alican.mvvm_starter.util.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.alican.mvvm_starter.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun TextView.txtColor(color: Int) {
    this.setTextColor(ContextCompat.getColor(this.context, color))
}

@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated("using for setImageResource")
fun ImageView.loadImageDrawable(drawableRes: Int) {
    Glide.with(this.context)
        .load(drawableRes)
        .apply(
            RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        )
        .error(R.drawable.ic_launcher_foreground)
        .into(this)

}

fun ImageView.loadImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        .error(R.drawable.ic_launcher_background)
        .into(this)
}

fun ImageView.loadRoundedImage(imageUrl: String?, radius: Float = 16f) {
    imageUrl?.let {
        if (it.isNotEmpty()) {
            Glide.with(context)
                .load(it)
                .apply(
                    RequestOptions.centerInsideTransform().transform(
                        CenterCrop(),
                        GranularRoundedCorners(radius, radius, radius, radius)
                    )
                )
                .error(R.drawable.ic_launcher_foreground)
                .into(this)
        }
    }
}

fun ImageView.loadCircleImage(imageUrl: String? = null, drawableRes: Int? = null) {
    drawableRes?.let {
        Glide.with(context)
            .load(it)
            .circleCrop()
            //.apply(RequestOptions.circleCropTransform().placeholder(R.mipmap.ic_launcher))
            .error(R.drawable.ic_launcher_foreground)
            .into(this)
    }

    imageUrl?.let {
        if (it.isNotEmpty()) {
            Glide.with(context)
                .load(it)
                .circleCrop()
                //.apply(RequestOptions.circleCropTransform().placeholder(R.mipmap.ic_launcher))
                .error(R.drawable.ic_launcher_foreground)
                .into(this)
        }
    }
}

fun TextInputEditText.clearInputError(
    tilInput: TextInputLayout,
    willChangeBackground: Boolean = false
) {
    addTextChangedListener(object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            tilInput.isErrorEnabled = false
            if (willChangeBackground) {
                if (s.isEmpty()) {
                    this@clearInputError.background =
                        ContextCompat.getDrawable(context, R.mipmap.ic_launcher)
                } else {
                    this@clearInputError.background =
                        ContextCompat.getDrawable(context, R.mipmap.ic_launcher)
                }
            }

        }

        override fun beforeTextChanged(
            s: CharSequence, start: Int, count: Int,
            after: Int,
        ) {
        }

        override fun afterTextChanged(s: Editable) {}
    })
}

fun Context.openPhoneDialer(phoneNumber: String): Boolean = try {
    val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null))
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)
    true
} catch (e: ActivityNotFoundException) {
    Log.e("PhoneUtils", e.toString())
    false
}

fun Context.openChromeTab(url: String) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(this, Uri.parse(url))
}