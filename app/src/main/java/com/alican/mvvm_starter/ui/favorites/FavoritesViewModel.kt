package com.alican.mvvm_starter.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alican.mvvm_starter.data.local.model.FavoritesEntity
import com.alican.mvvm_starter.domain.interactor.FavoriteMoviesInteractor
import com.alican.mvvm_starter.domain.model.BaseUIModel
import com.alican.mvvm_starter.domain.model.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val interactor: FavoriteMoviesInteractor
) : ViewModel() {


    private val _favoriteMovies = interactor.getFavoriteMovies()
    val favoriteMovies: StateFlow<BaseUIModel<List<FavoritesEntity>>> =
        _favoriteMovies.stateIn(viewModelScope, SharingStarted.Eagerly, Loading())
}