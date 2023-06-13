package com.alican.mvvm_starter.domain.interactor

import androidx.paging.PagingData
import androidx.paging.map
import com.alican.mvvm_starter.data.repository.ListMoviesRepository
import com.alican.mvvm_starter.domain.mapper.MovieMapper
import com.alican.mvvm_starter.domain.model.MovieUIModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesListInteractor @Inject constructor( private val repository: ListMoviesRepository,private val mapper: MovieMapper) {

    fun getUpcomingMovies(): Flow<PagingData<MovieUIModel>> {
        return repository.getUpComingMovie()
            .map { pagingData -> pagingData.map { mapper.mapOnMovieResponse(it) } }
    }
    fun getTopRatedMovies(): Flow<PagingData<MovieUIModel>> {
        return repository.getTopRatedMovie()
            .map { pagingData -> pagingData.map { mapper.mapOnMovieResponse(it) } }
    }
    fun getPopularMovies(): Flow<PagingData<MovieUIModel>> {
        return repository.getPopularMovie()
            .map { pagingData -> pagingData.map { mapper.mapOnMovieResponse(it) } }
    }
    fun getNowPlayingMovies(): Flow<PagingData<MovieUIModel>> {
        return repository.getNowPlayingMovies()
            .map { pagingData -> pagingData.map { mapper.mapOnMovieResponse(it) } }
    }
}