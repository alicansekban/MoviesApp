package com.alican.mvvm_starter.domain.mapper

import com.alican.mvvm_starter.data.local.model.MovieEntity
import com.alican.mvvm_starter.data.model.MovieResponseModel

fun MovieResponseModel.toMovieEntity() : MovieEntity {
    return MovieEntity(
        adult = this.adult,
        backdrop_path = this.backdrop_path,
        movie_id = this.id,
        original_language = this.original_language,
        overview = this.overview,
        popularity = this.popularity,
        poster_path = this.poster_path,
        release_date = this.release_date,
        title = this.title,
        video = this.video,
        vote_average = this.vote_average,
        vote_count = this.vote_count
    )
}