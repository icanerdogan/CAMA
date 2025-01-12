package com.ibrahimutkusarican.cleanarchitecturemovieapp.features.seeall.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ibrahimutkusarican.cleanarchitecturemovieapp.features.home.data.local.entity.MovieType
import com.ibrahimutkusarican.cleanarchitecturemovieapp.features.home.data.remote.MovieRemoteDataSource
import com.ibrahimutkusarican.cleanarchitecturemovieapp.features.home.data.remote.response.MovieResultResponse
import retrofit2.HttpException
import java.io.IOException

class SeeAllMoviePagingSource(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieType: MovieType,
) : PagingSource<Int, MovieResultResponse>() {
    override fun getRefreshKey(state: PagingState<Int, MovieResultResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResultResponse> {
        try {
            val nextPageNumber = params.key ?: STARTING_PAGE_INDEX
            val movieResponse = when (movieType) {
                MovieType.NOW_PLAYING -> movieRemoteDataSource.getNowPlayingMovies(nextPageNumber)
                MovieType.POPULAR -> movieRemoteDataSource.getPopularMovies(nextPageNumber)
                MovieType.TOP_RATED -> movieRemoteDataSource.getTopRatedMovies(nextPageNumber)
                MovieType.UPCOMING -> movieRemoteDataSource.getUpComingMovies(nextPageNumber)
            }
            return LoadResult.Page(
                data = movieResponse.movieResultResponse,
                prevKey = if (nextPageNumber == STARTING_PAGE_INDEX) null else nextPageNumber - 1,
                nextKey = if (movieResponse.movieResultResponse.isEmpty()) null else movieResponse.page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}