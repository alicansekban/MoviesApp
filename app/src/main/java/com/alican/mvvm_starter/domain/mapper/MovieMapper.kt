package com.alican.mvvm_starter.domain.mapper

import com.alican.mvvm_starter.data.model.MovieCreditResponse
import com.alican.mvvm_starter.data.model.MovieDetailResponse
import com.alican.mvvm_starter.data.model.MovieResponseModel
import com.alican.mvvm_starter.data.model.MovieReviewResponse
import com.alican.mvvm_starter.domain.model.AuthorDetails
import com.alican.mvvm_starter.domain.model.Cast
import com.alican.mvvm_starter.domain.model.Genre
import com.alican.mvvm_starter.domain.model.MovieCreditsUIModel
import com.alican.mvvm_starter.domain.model.MovieDetailReviewsUIModel
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
        )
    }

    fun mapMovieDetailResponseToMovieDetailUIModel(movieDetailResponse: MovieDetailResponse): MovieDetailUIModel {
        val genres = movieDetailResponse.genres?.map { genreItem ->
            Genre(genreItem?.id, genreItem?.name)
        }
        return MovieDetailUIModel(
            movieDetailResponse.id,
            movieDetailResponse.title,
            movieDetailResponse.originalTitle,
            movieDetailResponse.poster_path,
            movieDetailResponse.backdrop_path,
            movieDetailResponse.vote_average,
            genres
        )
    }

    fun mapMovieReviewResponseToMovieReviewUIModel(movieReviewResponse: MovieReviewResponse): MovieDetailReviewsUIModel {
        val authorDetails = movieReviewResponse.author_details?.let { authorDetails ->
            AuthorDetails(
                authorDetails.avatar_path,
                authorDetails.name,
                authorDetails.rating,
                authorDetails.username
            )
        }

        return MovieDetailReviewsUIModel(
            movieReviewResponse.id,
            movieReviewResponse.author,
            movieReviewResponse.content,
            movieReviewResponse.url,
            movieReviewResponse.created_at,
            movieReviewResponse.updated_at,
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