package com.alican.mvvm_starter.domain.interactor

import com.alican.mvvm_starter.data.repository.MovieDetailRepository
import com.alican.mvvm_starter.domain.mapper.MovieMapper
import com.alican.mvvm_starter.domain.model.BaseUIModel
import com.alican.mvvm_starter.domain.model.Error
import com.alican.mvvm_starter.domain.model.Loading
import com.alican.mvvm_starter.domain.model.MovieUIModel
import com.alican.mvvm_starter.domain.model.Success
import com.alican.mvvm_starter.util.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.nio.channels.Pipe
import javax.inject.Inject

class MovieDetailInteractor @Inject constructor(val repository: MovieDetailRepository,val mapper: MovieMapper) {

    fun getUpcomingMovies(id:Int): Flow<BaseUIModel<MovieUIModel>> {
        return flow {
            emit(Loading())
            emit(
                when (val result = repository.getMovieDetail(id)) {
                    is ResultWrapper.GenericError -> Error("Bir hata olustu!")
                    ResultWrapper.Loading -> Loading()
                    ResultWrapper.NetworkError -> Error("Internetinizi kontrol edin!")
                    is ResultWrapper.Success -> Success(mapper.mapOnMovieResponse(result.value))
                }
            )
        }
    }
}