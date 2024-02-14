package org.themoviedb.example.home.domain.repository

import kotlinx.coroutines.flow.Flow
import org.themoviedb.example.home.model.WeeklyTrendingMovies
import retrofit2.Response

interface TvShowsRemoteDataSource {

    suspend fun getTrendingLists(): Response<WeeklyTrendingMovies>

    suspend fun getSearchLists(query: String): Response<WeeklyTrendingMovies>
}