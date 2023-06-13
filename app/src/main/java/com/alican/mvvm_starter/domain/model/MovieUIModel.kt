package com.alican.mvvm_starter.domain.model

import android.os.Parcelable
import com.alican.mvvm_starter.util.Constant
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieUIModel(
    val id: Int = 0,
    val original_language: String,
    val poster_path: String?,
    val release_date: String?,
    val title: String,
    val vote_average: Double?,
) : Parcelable {

    fun getImagePath() : String {
        return Constant.BASE_POSTER_URL + poster_path
    }
    fun rateFormat(): String {
        return String.format("%.1f", vote_average)
    }
}