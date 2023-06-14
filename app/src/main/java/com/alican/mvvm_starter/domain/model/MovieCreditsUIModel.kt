package com.alican.mvvm_starter.domain.model

import android.os.Parcelable
import com.alican.mvvm_starter.util.Constant
import kotlinx.parcelize.Parcelize

data class MovieCreditsUIModel(
    val id: Int?,
    val cast: List<Cast>?,
)
@Parcelize
data class Cast(
    val castId: Int?,
    val character: String?,
    val gender: Int?,
    val creditId: String?,
    val knownForDepartment: String?,
    val originalName: String?,
    val popularity: Double?,
    val name: String?,
    val profilePath: String?,
    val id: Int?,
    val adult: Boolean?,
    val order: Int?
) : Parcelable {
    fun getImagePath(): String {
        return Constant.BASE_POSTER_URL + profilePath
    }
}


