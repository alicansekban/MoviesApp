package com.alican.mvvm_starter.ui.list

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.alican.mvvm_starter.data.repository.ListMoviesRepository
import com.alican.mvvm_starter.domain.interactor.MoviesListInteractor
import com.alican.mvvm_starter.domain.model.MovieUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val repository: ListMoviesRepository,
     val interactor: MoviesListInteractor
) : ViewModel() {


     var _movies = interactor.getPopularMovies()
    val movies: Flow<PagingData<MovieUIModel>> get() = _movies


}