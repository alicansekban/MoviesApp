package com.alican.mvvm_starter.data.remote.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.alican.mvvm_starter.data.local.AppDatabase
import com.alican.mvvm_starter.data.local.model.RemoteKey
import com.alican.mvvm_starter.data.local.model.ReviewsEntity
import com.alican.mvvm_starter.data.remote.api.WebService
import com.alican.mvvm_starter.domain.mapper.MovieMapper
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class HomeMoviesMediator @Inject constructor(
    private val database: AppDatabase,
    private val webService: WebService,
    private val mapper : MovieMapper,
    private val id :Int
) : RemoteMediator<Int, ReviewsEntity>() {

    private val remoteKeyDao = database.remoteKeyDao()
    private val movieDao = database.reviewsDao()
    override suspend fun initialize(): InitializeAction {
        val remoteKey = database.withTransaction {
            remoteKeyDao.getKeyByMovie("discover_movie")
        } ?: return InitializeAction.LAUNCH_INITIAL_REFRESH

        val cacheTimeout = TimeUnit.HOURS.convert(1, TimeUnit.MILLISECONDS)

        return if ((System.currentTimeMillis() - remoteKey.last_updated) >= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ReviewsEntity>
    ): MediatorResult {
        return try {
            val page = when(loadType) {
                LoadType.REFRESH -> {
                    1
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(true)
                }
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        remoteKeyDao.getKeyByMovie("discover_movie")
                    } ?: return MediatorResult.Success(true)

                    if(remoteKey.next_page == null) {
                        return MediatorResult.Success(true)
                    }

                    remoteKey.next_page
                }
            }

            val result = webService.getMovieReviews(
                id,
                page = page,
            )

            database.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    movieDao.clearReviews()
                }

                val nextPage = if(result.results.isEmpty()) {
                    null
                } else {
                    page+1
                }

                val reviewsEntity = result.results.map {
                    mapper.mapMovieReviewResponseToMovieReviewUIModel(it)
                }

                remoteKeyDao.insertKey(
                    RemoteKey(
                    id = "discover_movie",
                    next_page = nextPage,
                    last_updated = System.currentTimeMillis()
                )
                )
                movieDao.insertReviews(reviewsEntity)
            }

            MediatorResult.Success(
                endOfPaginationReached = result.results.isEmpty()
            )
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}