package com.alican.mvvm_starter.domain.mapper

import com.alican.mvvm_starter.data.model.MovieCreditResponse
import com.alican.mvvm_starter.data.model.MovieDetailResponse
import com.alican.mvvm_starter.data.model.MovieResponseModel
import com.alican.mvvm_starter.data.model.MovieReviewResponse
import com.alican.mvvm_starter.domain.model.AuthorDetails
import com.alican.mvvm_starter.domain.model.BelongsToCollection
import com.alican.mvvm_starter.domain.model.Cast
import com.alican.mvvm_starter.domain.model.Crew
import com.alican.mvvm_starter.domain.model.Genre
import com.alican.mvvm_starter.domain.model.MovieCreditsUIModel
import com.alican.mvvm_starter.domain.model.MovieDetailReviewsUIModel
import com.alican.mvvm_starter.domain.model.MovieDetailUIModel
import com.alican.mvvm_starter.domain.model.MovieUIModel
import com.alican.mvvm_starter.domain.model.ProductionCompany
import com.alican.mvvm_starter.domain.model.ProductionCountry
import com.alican.mvvm_starter.domain.model.SpokenLanguage
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
            Genre(genreItem?.id, genreItem?.name)
        }

        val spokenLanguages = movieDetailResponse.spoken_languages?.map { spokenLanguageItem ->
            SpokenLanguage(
                spokenLanguageItem?.iso_639_1,
                spokenLanguageItem?.name,
                spokenLanguageItem?.englishName
            )
        }

        val productionCountries = movieDetailResponse.productionCountries?.map { productionCountryItem ->
            ProductionCountry(
                productionCountryItem?.iso_3166_1,
                productionCountryItem?.name
            )
        }

        val productionCompanies = movieDetailResponse.production_companies?.map { productionCompanyItem ->
            ProductionCompany(
                productionCompanyItem?.id,
                productionCompanyItem?.name,
                productionCompanyItem?.logo_path,
                productionCompanyItem?.origin_country
            )
        }

        val belongsToCollection = movieDetailResponse.belongs_to_collection?.let { belongsToCollection ->
            BelongsToCollection(
                belongsToCollection.id,
                belongsToCollection.name,
                belongsToCollection.poster_path,
                belongsToCollection.backdrop_path
            )
        }

        return MovieDetailUIModel(
            movieDetailResponse.id,
            movieDetailResponse.title,
            movieDetailResponse.originalTitle,
            movieDetailResponse.poster_path,
            movieDetailResponse.backdrop_path,
            movieDetailResponse.overview,
            movieDetailResponse.release_date,
            movieDetailResponse.vote_average,
            movieDetailResponse.voteCount,
            movieDetailResponse.popularity,
            movieDetailResponse.budget,
            movieDetailResponse.revenue,
            movieDetailResponse.runtime,
            movieDetailResponse.original_language,
            movieDetailResponse.imdb_id,
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