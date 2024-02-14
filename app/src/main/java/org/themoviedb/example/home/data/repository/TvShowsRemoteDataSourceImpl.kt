package org.themoviedb.example.home.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import org.themoviedb.example.home.domain.repository.TvShowsRemoteDataSource
import org.themoviedb.example.home.model.WeeklyTrendingMovies
import org.themoviedb.example.home.repository.TvShowsApiInterface
import retrofit2.Response
import javax.inject.Inject

class TvShowsRemoteDataSourceImpl @Inject constructor(
    private val apiInterface: TvShowsApiInterface,
): TvShowsRemoteDataSource {
    override suspend fun getTrendingLists(): Response<WeeklyTrendingMovies> {
        return apiInterface.getTrendingLists()
    }

    override suspend fun getSearchLists(query: String): Response<WeeklyTrendingMovies> {
        return apiInterface.getSearchLists(query = query)
    }
}