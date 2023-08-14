package com.alican.mvvm_starter.ui.list

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.domain.interactor.FavoriteMoviesInteractor
import com.alican.mvvm_starter.domain.interactor.MoviesListInteractor
import com.alican.mvvm_starter.domain.model.BaseUIModel
import com.alican.mvvm_starter.domain.model.Loading
import com.alican.mvvm_starter.domain.model.MovieUIModel
import com.alican.mvvm_starter.util.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    val interactor: MoviesListInteractor,
    val favoritesInteractor: FavoriteMoviesInteractor,
    savedStateHandle: SavedStateHandle,
    @ApplicationContext private val context: Context
) : ViewModel() {


    private val _movies = MutableStateFlow<PagingData<MovieUIModel>>(PagingData.empty())
    val movies: StateFlow<PagingData<MovieUIModel>>
        get() = _movies

    private val _favorites = MutableStateFlow<BaseUIModel<Any>>(Loading())
    val favorites: StateFlow<BaseUIModel<Any>>
        get() = _favorites.stateIn(
            viewModelScope,
            SharingStarted.Eagerly, Loading()
        )

    var title: String = ""
    val argument = checkNotNull(savedStateHandle.get<String>("type"))

    init {
        getData(argument)
    }

    fun setTitle(): String {
        return when (argument) {
            Constant.POPULAR_MOVIES -> {
                context.getString(R.string.txt_popular_movies)
            }

            Constant.UP_COMING_MOVIES -> {
                context.getString(R.string.txt_upcoming_movies)
            }

            Constant.TOP_RATED_MOVIES -> {
                context.getString(R.string.txt_top_rated_movies)
            }

            Constant.NOW_PLAYING -> {
                context.getString(R.string.txt_now_playing_movies)
            }

            else -> {
                ""
            }
        }
    }

    fun addToFavorites(movie: MovieUIModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                favoritesInteractor.insertFavoriteMovie(movie).collect{
                    _favorites.emit(it)
                }
            } catch (e: Exception) {
                //
            }
        }
    }

    fun favoritesEmitted() {
        viewModelScope.launch {
            _favorites.emit(Loading())
        }
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                interactor.searchMovies(query).cachedIn(viewModelScope)
                    .collect { movies ->
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
                    .collect { movies ->
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
                    .collect { movies ->
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
                    .collect { movies ->
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
                    .collect { movies ->
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