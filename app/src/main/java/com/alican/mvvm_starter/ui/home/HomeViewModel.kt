package com.alican.mvvm_starter.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.murgupluoglu.request.RESPONSE
import com.murgupluoglu.request.request
import com.alican.mvvm_starter.data.remote.webservice.WebService
import com.alican.mvvm_starter.util.SingleLiveEvent
import com.alican.mvvm_starter.base.BaseResponse
import com.alican.mvvm_starter.domain.repository.HomeMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
   private val webService: WebService,
   private val repository: HomeMoviesRepository

) : ViewModel() {
    val movies = repository.discoverMovie()
        .flowOn(Dispatchers.IO)
        .cachedIn(viewModelScope)
}
