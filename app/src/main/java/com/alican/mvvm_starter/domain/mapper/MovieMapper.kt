package com.alican.mvvm_starter.domain.mapper

import com.alican.mvvm_starter.data.model.MovieModel
import com.alican.mvvm_starter.data.model.MovieResponseModel
import com.alican.mvvm_starter.domain.model.MovieUIModel
import javax.inject.Inject

class MovieMapper @Inject constructor() {
    fun mapOnMovieResponse(model: MovieResponseModel): MovieUIModel {
        return MovieUIModel(
            adult = model.adult,
            backdrop_path = model.backdrop_path,
            id = model.id,
            original_language = model.original_language,
            overview = model.overview,
            popularity = model.popularity,
            poster_path = model.poster_path,
            release_date = model.release_date,
            title = model.title,
            video = model.video,
            vote_average = model.vote_average,
            vote_count = model.vote_count,
            genre_ids = model.genre_ids,
            original_title = model.original_title
        )
    }
}