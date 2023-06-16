package com.alican.mvvm_starter.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.alican.mvvm_starter.data.local.model.ReviewsEntity
import com.alican.mvvm_starter.data.repository.HomeMoviesRepository
import com.alican.mvvm_starter.domain.interactor.MovieDetailInteractor
import com.alican.mvvm_starter.domain.model.BaseUIModel
import com.alican.mvvm_starter.domain.model.Loading
import com.alican.mvvm_starter.domain.model.MovieCreditsUIModel
import com.alican.mvvm_starter.domain.model.MovieDetailUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
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


    private val _reviews = MutableStateFlow<PagingData<ReviewsEntity>>(PagingData.empty())
    val reviews: StateFlow<PagingData<ReviewsEntity>> get() = _reviews

    fun getReviews(id: Int) {
        viewModelScope.launch {
            try {
                repository.discoverMovie(id)
                    .collectLatest { movies ->
                        _reviews.emit(movies)
                    }
            } catch (e: Exception) {
                // Hata durumunda gerekli işlemleri yapabilirsiniz
            }
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

}

