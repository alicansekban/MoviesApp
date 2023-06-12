package com.alican.mvvm_starter.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
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


    private val _popularMovies = MutableStateFlow<PagingData<MovieModel>>(PagingData.empty())
    val popularMovies: StateFlow<PagingData<MovieModel>> get() = _popularMovies


    init {
        getPopularMovies()
    }
    fun getPopularMovies() {
        viewModelScope.launch {
            repository.getPopularMovie().collectLatest {
                _popularMovies.emit(it)

            }
        }
    }

    fun getUpComingMovies() {
        viewModelScope.launch {
            repository.getUpComingMovie().collectLatest {
                _popularMovies.emit(it)

            }
        }
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            repository.getTopRatedMovie().collectLatest {
                _popularMovies.emit(it)

            }
        }
    }

    fun getMoviesWithQuery(query:String) {
        viewModelScope.launch {
            repository.getSearchMovies(query).collectLatest {
                _popularMovies.emit(it)

            }
        }
    }
}