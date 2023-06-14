package com.alican.mvvm_starter.domain.mapper

import com.alican.mvvm_starter.data.model.MovieCreditResponse
import com.alican.mvvm_starter.data.model.MovieDetailResponse
import com.alican.mvvm_starter.data.model.MovieResponseModel
import com.alican.mvvm_starter.data.model.MovieReviewResponse
import com.alican.mvvm_starter.domain.model.AuthorDetails
import com.alican.mvvm_starter.domain.model.Cast
import com.alican.mvvm_starter.domain.model.Crew
import com.alican.mvvm_starter.domain.model.MovieCreditsUIModel
import com.alican.mvvm_starter.domain.model.MovieDetailReviewsUIModel
import com.alican.mvvm_starter.domain.model.MovieDetailUIModel
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

    fun mapMovieDetailResponseToMovieDetailUIModel(movieDetailResponse: MovieDetailResponse): MovieDetailUIModel {
        val genres = movieDetailResponse.genres?.map { genreItem ->
            MovieDetailUIModel.Genre(genreItem?.id, genreItem?.name)
        }

        val spokenLanguages = movieDetailResponse.spokenLanguages?.map { spokenLanguageItem ->
            MovieDetailUIModel.SpokenLanguage(
                spokenLanguageItem?.iso6391,
                spokenLanguageItem?.name,
                spokenLanguageItem?.englishName
            )
        }

        val productionCountries = movieDetailResponse.productionCountries?.map { productionCountryItem ->
            MovieDetailUIModel.ProductionCountry(
                productionCountryItem?.iso31661,
                productionCountryItem?.name
            )
        }

        val productionCompanies = movieDetailResponse.productionCompanies?.map { productionCompanyItem ->
            MovieDetailUIModel.ProductionCompany(
                productionCompanyItem?.id,
                productionCompanyItem?.name,
                productionCompanyItem?.logoPath,
                productionCompanyItem?.originCountry
            )
        }

        val belongsToCollection = movieDetailResponse.belongsToCollection?.let { belongsToCollection ->
            MovieDetailUIModel.BelongsToCollection(
                belongsToCollection.id,
                belongsToCollection.name,
                belongsToCollection.posterPath,
                belongsToCollection.backdropPath
            )
        }

        return MovieDetailUIModel(
            movieDetailResponse.id,
            movieDetailResponse.title,
            movieDetailResponse.originalTitle,
            movieDetailResponse.posterPath,
            movieDetailResponse.backdropPath,
            movieDetailResponse.overview,
            movieDetailResponse.releaseDate,
            movieDetailResponse.voteAverage,
            movieDetailResponse.voteCount,
            movieDetailResponse.popularity,
            movieDetailResponse.budget,
            movieDetailResponse.revenue,
            movieDetailResponse.runtime,
            movieDetailResponse.originalLanguage,
            movieDetailResponse.imdbId,
            movieDetailResponse.video,
            movieDetailResponse.adult,
            movieDetailResponse.homepage,
            movieDetailResponse.status,
            genres,
            spokenLanguages,
            productionCountries,
            productionCompanies,
            belongsToCollection
        )
    }

    fun mapMovieReviewResponseToMovieReviewUIModel(movieReviewResponse: MovieReviewResponse): MovieDetailReviewsUIModel {
        val authorDetails = movieReviewResponse.authorDetails?.let { authorDetails ->
            AuthorDetails(
                authorDetails.avatarPath,
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
            movieReviewResponse.createdAt,
            movieReviewResponse.updatedAt,
            authorDetails
        )
    }

    fun mapMovieCreditResponseToMovieCreditsUIModel(movieCreditResponse: MovieCreditResponse): MovieCreditsUIModel {
        val cast = movieCreditResponse.cast?.map { castItem ->
            Cast(
                castItem?.castId,
                castItem?.character,
                castItem?.gender,
                castItem?.creditId,
                castItem?.knownForDepartment,
                castItem?.originalName,
                castItem?.popularity,
                castItem?.name,
                castItem?.profilePath,
                castItem?.id,
                castItem?.adult,
                castItem?.order
            )
        }

        val crew = movieCreditResponse.crew?.map { crewItem ->
            Crew(
                crewItem?.gender,
                crewItem?.creditId,
                crewItem?.knownForDepartment,
                crewItem?.originalName,
                crewItem?.popularity,
                crewItem?.name,
                crewItem?.profilePath,
                crewItem?.id,
                crewItem?.adult,
                crewItem?.department,
                crewItem?.job
            )
        }

        return MovieCreditsUIModel(
            movieCreditResponse.id,
            cast,
            crew
        )
    }


}