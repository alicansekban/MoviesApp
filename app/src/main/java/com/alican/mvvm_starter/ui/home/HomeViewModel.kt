package com.alican.mvvm_starter.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alican.mvvm_starter.base.BasePagingResponse
import com.murgupluoglu.request.RESPONSE
import com.murgupluoglu.request.request
import com.alican.mvvm_starter.data.remote.webservice.WebService
import com.alican.mvvm_starter.util.SingleLiveEvent
import com.alican.mvvm_starter.base.BaseResponse
import com.alican.mvvm_starter.data.local.model.MovieEntity
import com.alican.mvvm_starter.data.model.MovieModel
import com.alican.mvvm_starter.data.model.MovieResponseModel
import com.alican.mvvm_starter.domain.repository.HomeMoviesRepository
import com.alican.mvvm_starter.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val webService: WebService,
    val homeRepository: HomeRepository,
    repository: HomeMoviesRepository

) : ViewModel() {

    val dataResponse: MutableLiveData<RESPONSE<BasePagingResponse<MovieResponseModel>>> =
        MutableLiveData()

    fun getData() {
        dataResponse.request({ webService.getUpComingMovies(1) })
    }

    val movies = repository.discoverMovie()
        .flowOn(Dispatchers.IO)
        .cachedIn(viewModelScope)
}
