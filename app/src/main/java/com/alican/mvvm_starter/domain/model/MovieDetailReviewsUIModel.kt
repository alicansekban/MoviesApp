package com.alican.mvvm_starter.domain.model

import android.os.Parcelable
import com.alican.mvvm_starter.util.Constant
import kotlinx.parcelize.Parcelize

data class MovieDetailReviewsUIModel(
    val id: String?,
    val author: String?,
    val content: String?,
    val authorDetails: AuthorDetails?
)
@Parcelize
data class AuthorDetails(
    val avatarPath: String?,
    val name: String?,
    val rating: Double?,
    val username: String?
) : Parcelable {

    fun getImage() : String {
        return Constant.BASE_POSTER_URL + avatarPath
    }
}

