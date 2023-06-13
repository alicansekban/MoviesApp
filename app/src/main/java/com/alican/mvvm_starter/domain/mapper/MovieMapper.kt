package com.alican.mvvm_starter.domain.mapper

import com.alican.mvvm_starter.data.model.MovieModel
import com.alican.mvvm_starter.data.model.MovieResponseModel
import com.alican.mvvm_starter.domain.model.MovieUIModel
import javax.inject.Inject

class MovieMapper @Inject constructor() {
    fun mapOnMovieResponse(model: MovieResponseModel): MovieUIModel {
        return MovieUIModel(
            model.id,
            model.original_language,
            model.poster_path,
            model.release_date,
            model.title,
            model.vote_average
        )
    }
}