package com.alican.mvvm_starter.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.alican.mvvm_starter.data.model.MovieModel
import com.alican.mvvm_starter.domain.repository.ListMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val repository: ListMoviesRepository
) : ViewModel() {


    private val _movies = MutableStateFlow<PagingData<MovieModel>>(PagingData.empty())
    val movies: StateFlow<PagingData<MovieModel>> get() = _movies


    fun getPopularMovies() {
        viewModelScope.launch {
            repository.getPopularMovie().cachedIn(viewModelScope).collectLatest {
                _movies.emit(it)

            }
        }
    }

    fun getUpComingMovies() {
        viewModelScope.launch {
            repository.getUpComingMovie().cachedIn(viewModelScope).collectLatest {
                _movies.emit(it)

            }
        }
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            repository.getTopRatedMovie().cachedIn(viewModelScope).collectLatest {
                _movies.emit(it)

            }
        }
    }

    fun getMoviesWithQuery(query: String = "") {
        viewModelScope.launch {
            repository.getSearchMovies(query).cachedIn(viewModelScope).collectLatest {
                _movies.emit(it)
            }
        }
    }
}