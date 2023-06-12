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

    val exampleListDataFlow = MutableStateFlow<List<MovieModel>>(emptyList())
    val myList: StateFlow<List<MovieModel>> get() = exampleListDataFlow.asStateFlow()


    private val _popularMovies = MutableStateFlow<List<MovieModel>>(emptyList())
    val popularMovies: StateFlow<List<MovieModel>> get() = _popularMovies.asStateFlow()


    private val _upComingMovies = MutableStateFlow<List<MovieModel>>(emptyList())
    val upComingMovies: StateFlow<List<MovieModel>> get() = _upComingMovies.asStateFlow()

    init {
        getDataExample()
        getPopularMovies()
        getUpcomingMovies()
    }

    fun getDataExample() {
        progressLiveData.postValue(true)
        viewModelScope.launch {
            repository.getExampleData().collectLatest {
                exampleListDataFlow.emit(it)
                progressLiveData.postValue(false)
            }
        }
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
    val movies = repository.discoverMovie()
        .flowOn(Dispatchers.IO)
        .cachedIn(viewModelScope)
}
