package com.alican.mvvm_starter.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alican.mvvm_starter.data.local.model.FavoritesEntity
import com.alican.mvvm_starter.domain.model.BaseUIModel
import com.alican.mvvm_starter.domain.model.Loading
import com.alican.mvvm_starter.domain.usecase.FavoriteMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val useCase: FavoriteMoviesUseCase
) : ViewModel() {


    private val _favoriteMovies = MutableStateFlow<BaseUIModel<List<FavoritesEntity>>>(Loading())
    val favoriteMovies: StateFlow<BaseUIModel<List<FavoritesEntity>>> get() = _favoriteMovies

    init {
        getFavorites()
    }

    fun getFavorites(searchQuery: String = "") {
        viewModelScope.launch(Dispatchers.IO) {
            useCase(searchQuery).collectLatest {
                _favoriteMovies.emit(it)
            }
        }
    }
}