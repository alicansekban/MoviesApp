package com.alican.mvvm_starter.domain.mapper

import com.alican.mvvm_starter.data.local.model.AuthorDetails
import com.alican.mvvm_starter.data.local.model.ReviewsEntity
import com.alican.mvvm_starter.data.model.MovieCreditResponse
import com.alican.mvvm_starter.data.model.MovieDetailResponse
import com.alican.mvvm_starter.data.model.MovieResponseModel
import com.alican.mvvm_starter.data.model.MovieReviewResponse
import com.alican.mvvm_starter.domain.model.Cast
import com.alican.mvvm_starter.domain.model.Genre
import com.alican.mvvm_starter.domain.model.MovieCreditsUIModel
import com.alican.mvvm_starter.domain.model.MovieDetailUIModel
import com.alican.mvvm_starter.domain.model.MovieUIModel
import javax.inject.Inject

class MovieMapper @Inject constructor() {
    fun mapOnMovieResponse(model: MovieResponseModel): MovieUIModel {
        return MovieUIModel(
            id = model.id,
            original_language = model.original_language,
            overview = model.overview,
            poster_path = model.poster_path,
            release_date = model.release_date,
            title = model.title,
            vote_average = model.vote_average,
            adult = model.adult
        )
    }

    fun mapMovieDetailResponseToMovieDetailUIModel(movieDetailResponse: MovieDetailResponse): MovieDetailUIModel {
        val genres = movieDetailResponse.genres?.map { genreItem ->
            Genre(genreItem?.id, genreItem?.name)
        }
        return MovieDetailUIModel(
            id = movieDetailResponse.id,
            title = movieDetailResponse.title,
            posterPath = movieDetailResponse.poster_path,
            overview = movieDetailResponse.overview,
            releaseDate = movieDetailResponse.release_date,
            voteAverage = movieDetailResponse.vote_average,
            genres = genres
        )
    }

    fun mapMovieReviewResponseToMovieReviewUIModel(movieReviewResponse: MovieReviewResponse): ReviewsEntity {
        val authorDetails = movieReviewResponse.author_details?.let { authorDetails ->
            AuthorDetails(
                authorDetails.avatar_path,
                authorDetails.name,
                authorDetails.rating,
                authorDetails.username
            )
        }

        return ReviewsEntity(
            movieReviewResponse.id.toString(),
            movieReviewResponse.author,
            movieReviewResponse.content,
            authorDetails
        )
    }

    fun mapMovieCreditResponseToMovieCreditsUIModel(movieCreditResponse: MovieCreditResponse): MovieCreditsUIModel {
        val cast = movieCreditResponse.cast?.map { castItem ->
            Cast(
                castItem?.cast_id,
                castItem?.character,
                castItem?.name,
                castItem?.profile_path,
                castItem?.id
            )
        }
        return MovieCreditsUIModel(
            movieCreditResponse.id,
            cast
        )
    }


}