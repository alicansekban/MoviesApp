package com.alican.mvvm_starter.domain.model

import android.os.Parcelable
import com.alican.mvvm_starter.util.Constant
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieUIModel(
    val id: Int,
    val original_language: String,
    val overview: String,
    val poster_path: String? = null,
    val release_date: String,
    val title: String,
    val vote_average: Double,
    val adult : Boolean
) : Parcelable {

    fun getImagePath(): String {
        return Constant.BASE_POSTER_URL + poster_path
    }

    fun getTitleText() : String{
        return title
    }

    fun rateFormat(): String {
        return String.format("%.1f", vote_average)
    }
}