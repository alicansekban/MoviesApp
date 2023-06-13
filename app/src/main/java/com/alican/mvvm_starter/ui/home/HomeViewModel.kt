package com.alican.mvvm_starter.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alican.mvvm_starter.data.remote.webservice.WebService
import com.alican.mvvm_starter.data.model.MovieModel
import com.alican.mvvm_starter.domain.repository.HomeMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val webService: WebService,
    val repository: HomeMoviesRepository

) : ViewModel() {
    val progressLiveData: MutableLiveData<Boolean> = MutableLiveData()



    private val _popularMovies = MutableStateFlow<List<MovieModel>>(emptyList())
    val popularMovies: StateFlow<List<MovieModel>> get() = _popularMovies.asStateFlow()


    private val _upComingMovies = MutableStateFlow<List<MovieModel>>(emptyList())
    val upComingMovies: StateFlow<List<MovieModel>> get() = _upComingMovies.asStateFlow()


    private val _topRatedMovies = MutableStateFlow<List<MovieModel>>(emptyList())
    val topRatedMovies: StateFlow<List<MovieModel>> get() = _topRatedMovies.asStateFlow()



    private val _nowPlayingMovies = MutableStateFlow<List<MovieModel>>(emptyList())
    val nowPlayingMovies: StateFlow<List<MovieModel>> get() = _nowPlayingMovies.asStateFlow()

    init {
        getPopularMovies()
        getUpcomingMovies()
        getTopRatedMovies()
        getNowPlayingMovies()
    }


    private fun getPopularMovies() {
        progressLiveData.postValue(true)
        viewModelScope.launch {
            repository.getPopularMovies().collectLatest {
                _popularMovies.emit(it)
                progressLiveData.postValue(false)

            }
        }
    }
    private fun getUpcomingMovies() {
        progressLiveData.postValue(true)
        viewModelScope.launch {
            repository.getUpComingMovies().collectLatest {
                _upComingMovies.emit(it)
                progressLiveData.postValue(false)

            }
        }
    }

    private fun getTopRatedMovies() {
        progressLiveData.postValue(true)
        viewModelScope.launch {
            repository.getTopRatedMovies().collectLatest {
                _topRatedMovies.emit(it)
                progressLiveData.postValue(false)

            }
        }
    }

    private fun getNowPlayingMovies() {
        progressLiveData.postValue(true)
        viewModelScope.launch {
            repository.getNowPlayingMovies().collectLatest {
                _nowPlayingMovies.emit(it)
                progressLiveData.postValue(false)

            }
        }
    }

    val movies = repository.discoverMovie()
        .flowOn(Dispatchers.IO)
        .cachedIn(viewModelScope)
}
