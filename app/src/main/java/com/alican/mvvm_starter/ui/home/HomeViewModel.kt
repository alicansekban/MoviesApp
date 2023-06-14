package com.alican.mvvm_starter.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alican.mvvm_starter.data.remote.api.WebService
import com.alican.mvvm_starter.data.repository.HomeMoviesRepository
import com.alican.mvvm_starter.domain.interactor.HomeMoviesInteractor
import com.alican.mvvm_starter.domain.model.BaseUIModel
import com.alican.mvvm_starter.domain.model.Loading
import com.alican.mvvm_starter.domain.model.MovieUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val webService: WebService,
    val repository: HomeMoviesRepository,
    val interactor: HomeMoviesInteractor
) : ViewModel() {
    val progressLiveData: MutableLiveData<Boolean> = MutableLiveData()

    private val _popularMovies = interactor.getPopularMovies()
    val popularMovies: StateFlow<BaseUIModel<List<MovieUIModel>>> = _popularMovies.stateIn(viewModelScope, SharingStarted.Eagerly, Loading())


    private val _upComingMovies = interactor.getUpcomingMovies()
    val upComingMovies: StateFlow<BaseUIModel<List<MovieUIModel>>> = _upComingMovies.stateIn(viewModelScope, SharingStarted.Eagerly, Loading())


    private val _topRatedMovies = interactor.getTopRatedMovies()
    val topRatedMovies: StateFlow<BaseUIModel<List<MovieUIModel>>>  = _topRatedMovies.stateIn(viewModelScope, SharingStarted.Eagerly,Loading())



    private val _nowPlayingMovies = interactor.getNowPlayingMovies()
    val nowPlayingMovies:  StateFlow<BaseUIModel<List<MovieUIModel>>> = _nowPlayingMovies.stateIn(viewModelScope, SharingStarted.Eagerly,Loading())




    val movies = repository.discoverMovie()
        .flowOn(Dispatchers.IO)
        .cachedIn(viewModelScope)
}
