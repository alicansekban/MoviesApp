package com.alican.mvvm_starter.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alican.mvvm_starter.data.local.model.MovieEntity
import com.alican.mvvm_starter.data.repository.HomeMoviesRepository
import com.alican.mvvm_starter.domain.interactor.MovieDetailInteractor
import com.alican.mvvm_starter.domain.model.BaseUIModel
import com.alican.mvvm_starter.domain.model.Loading
import com.alican.mvvm_starter.domain.model.MovieCreditsUIModel
import com.alican.mvvm_starter.domain.model.MovieDetailReviewsUIModel
import com.alican.mvvm_starter.domain.model.MovieDetailUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    val interactor: MovieDetailInteractor,
    val repository: HomeMoviesRepository
) : ViewModel() {

    private val _movieDetail = MutableStateFlow<BaseUIModel<MovieDetailUIModel>>(Loading())
    val movieDetail: StateFlow<BaseUIModel<MovieDetailUIModel>> =
        _movieDetail.stateIn(viewModelScope, SharingStarted.Eagerly, Loading())


    private val _movieCredits = MutableStateFlow<BaseUIModel<MovieCreditsUIModel>>(Loading())
    val movieCredits: StateFlow<BaseUIModel<MovieCreditsUIModel>> =
        _movieCredits.stateIn(viewModelScope, SharingStarted.Eagerly, Loading())


    private val _reviews =
        MutableStateFlow<PagingData<MovieDetailReviewsUIModel>>(PagingData.empty())
    val reviews: StateFlow<PagingData<MovieDetailReviewsUIModel>> get() = _reviews


    private val _movieListState = MutableStateFlow<PagingData<MovieEntity>>(PagingData.empty())
    val movieListState: StateFlow<PagingData<MovieEntity>> get() = _movieListState

    fun getReviews(id: Int) {
        viewModelScope.launch {
            try {
                repository.discoverMovie()
                    .collectLatest { movies ->
                        _movieListState.emit(movies)
                    }
            } catch (e: Exception) {
                // Hata durumunda gerekli işlemleri yapabilirsiniz
            }
        }


        fun getMovieDetail(id: Int) {
            viewModelScope.launch {
                interactor.getMovieDetail(id).collectLatest {
                    _movieDetail.emit(it)
                }
            }
        }

        fun getMovieCredits(id: Int) {
            viewModelScope.launch {
                interactor.getMovieCredits(id).collectLatest {
                    _movieCredits.emit(it)
                }
            }
        }

        fun getMovieReviews(id: Int) {
            viewModelScope.launch {
                val movies = repository.discoverMovie()
                interactor.getMovieReviews(id).collectLatest {
                    _reviews.emit(it)
                }
            }
        }

        val movies = repository.discoverMovie()
            .flowOn(Dispatchers.IO)
            .cachedIn(viewModelScope)

    }

}