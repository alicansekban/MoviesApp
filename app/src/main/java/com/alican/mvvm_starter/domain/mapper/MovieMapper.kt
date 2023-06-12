package com.alican.mvvm_starter.domain.mapper

import com.alican.mvvm_starter.data.model.MovieModel
import com.alican.mvvm_starter.data.model.MovieResponseModel

class MovieMapper{

    fun mapOnMoviesList(orderResponse: List<MovieResponseModel>): List<MovieModel> {
        return orderResponse.map { response ->
            mapOnMovieResponse(response)
        }
    }

    private fun mapOnMovieResponse(model: MovieResponseModel): MovieModel {
        return MovieModel(
            adult = model.adult,
            backdrop_path = model.backdrop_path,
            movie_id = model.id,
            original_language = model.original_language,
            overview = model.overview,
            popularity = model.popularity,
            poster_path = model.poster_path,
            release_date = model.release_date,
            title = model.title,
            video = model.video,
            vote_average = model.vote_average,
            vote_count = model.vote_count
        )
    }
}