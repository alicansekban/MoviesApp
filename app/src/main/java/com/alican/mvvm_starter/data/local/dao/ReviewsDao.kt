package com.alican.mvvm_starter.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alican.mvvm_starter.data.local.model.ReviewsEntity

@Dao
interface ReviewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReview(movie: ReviewsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReviews(movies: List<ReviewsEntity>)

    @Query("delete from reviews")
    suspend fun clearReviews()

    @Query("select * from reviews order by id asc")
    fun getPagingReviews() : PagingSource<Int, ReviewsEntity>
}
