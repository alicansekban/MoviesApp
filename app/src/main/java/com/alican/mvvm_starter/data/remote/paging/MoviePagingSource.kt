package com.alican.mvvm_starter.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alican.mvvm_starter.data.model.MovieResponseModel
import com.alican.mvvm_starter.data.remote.api.WebService
import com.alican.mvvm_starter.util.MovieTypeEnum
import okio.IOException
import retrofit2.HttpException

class MoviePagingSource(
    private val api: WebService,
    private val movieTypeEnum: MovieTypeEnum,
    private val query: String = "",
) : PagingSource<Int, MovieResponseModel>() {
    override fun getRefreshKey(state: PagingState<Int, MovieResponseModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponseModel> {
        val page = params.key ?: 1

        return try {
            val response = when (movieTypeEnum) {
                MovieTypeEnum.POPULAR_MOVIES -> {
                    api.getPopularMovies(page)
                }

                MovieTypeEnum.TOP_RATED_MOVIES -> {
                    api.getTopRatedApi(page)
                }

                MovieTypeEnum.UP_COMING_MOVIES -> {
                    api.getUpComingMovies(page)
                }

                MovieTypeEnum.LATEST -> {
                    api.getLatestMovies(page)
                }

                MovieTypeEnum.NOW_PLAYING -> {
                    api.getNowPlayingMovies(page)
                }

                MovieTypeEnum.SEARCH_MOVIES -> {
                    api.getSearchMovieApi(page, query)
                }
            }

            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.results.isEmpty()) null else page.plus(1)
            )

        } catch (e: IOException) {
            LoadResult.Error(throwable = e)
        } catch (e: HttpException) {
            LoadResult.Error(throwable = e)
        }
    }
}