package com.alican.mvvm_starter.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alican.mvvm_starter.data.local.AppDatabase
import com.alican.mvvm_starter.data.local.model.MovieEntity
import com.alican.mvvm_starter.data.remote.mediator.HomeMoviesMediator
import com.alican.mvvm_starter.data.remote.webservice.WebService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeMoviesRepository @Inject constructor(
    private val webService: WebService,
    private val database: AppDatabase
) {
    @OptIn(ExperimentalPagingApi::class)
    fun discoverMovie(): Flow<PagingData<MovieEntity>> {
        val dbSource = {
            database.homeMoviesDao().getPagingMovie()
        }
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            remoteMediator = HomeMoviesMediator(
                database, webService
            ),
            pagingSourceFactory = dbSource
        )
            .flow
    }
}