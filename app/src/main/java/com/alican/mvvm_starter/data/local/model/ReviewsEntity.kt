package com.alican.mvvm_starter.data.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alican.mvvm_starter.util.Constant
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = "reviews"
)
data class ReviewsEntity(
    @PrimaryKey
    val id: String,
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