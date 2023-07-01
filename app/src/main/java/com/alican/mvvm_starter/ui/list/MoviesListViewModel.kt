package com.alican.mvvm_starter.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alican.mvvm_starter.data.repository.ListMoviesRepository
import com.alican.mvvm_starter.domain.interactor.MoviesListInteractor
import com.alican.mvvm_starter.domain.model.MovieUIModel
import com.alican.mvvm_starter.util.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val repository: ListMoviesRepository,
    val interactor: MoviesListInteractor
) : ViewModel() {


    private val _movies = MutableStateFlow<PagingData<MovieUIModel>>(PagingData.empty())
    val movies: StateFlow<PagingData<MovieUIModel>> get() = _movies


    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                interactor.searchMovies(query).cachedIn(viewModelScope)
                    .collectLatest { movies ->
                        _movies.emit(movies)
                    }
            } catch (e: Exception) {
                // Hata durumunda gerekli işlemleri yapabilirsiniz
            }
        }

    }

    fun getPopularMovies() {
        viewModelScope.launch {
            try {
                interactor.getPopularMovies().cachedIn(viewModelScope)
                    .collectLatest { movies ->
                        _movies.emit(movies)
                    }
            } catch (e: Exception) {
                // Hata durumunda gerekli işlemleri yapabilirsiniz
            }
        }

    }

    fun getNowPlaying() {
        viewModelScope.launch {
            try {
                interactor.getNowPlayingMovies().cachedIn(viewModelScope)
                    .collectLatest { movies ->
                        _movies.emit(movies)
                    }
            } catch (e: Exception) {
                // Hata durumunda gerekli işlemleri yapabilirsiniz
            }
        }

    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            try {
                interactor.getTopRatedMovies().cachedIn(viewModelScope)
                    .collectLatest { movies ->
                        _movies.emit(movies)
                    }
            } catch (e: Exception) {
                // Hata durumunda gerekli işlemleri yapabilirsiniz
            }
        }

    }

    fun getUpComingMovies() {
        viewModelScope.launch {
            try {
                interactor.getUpcomingMovies().cachedIn(viewModelScope)
                    .collectLatest { movies ->
                        _movies.emit(movies)
                    }
            } catch (e: Exception) {
                // Hata durumunda gerekli işlemleri yapabilirsiniz
            }
        }

    }

    fun getData(type: String?) {
        when (type) {
            Constant.POPULAR_MOVIES -> {
                getPopularMovies()
            }

            Constant.UP_COMING_MOVIES -> {
                getUpComingMovies()
            }

            Constant.TOP_RATED_MOVIES -> {
                getTopRatedMovies()
            }

            Constant.NOW_PLAYING -> {
                getNowPlaying()
            }
        }
    }
}