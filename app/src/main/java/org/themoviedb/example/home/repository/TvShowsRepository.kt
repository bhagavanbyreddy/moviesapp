package org.themoviedb.example.home.repository

import kotlinx.coroutines.flow.Flow
import org.themoviedb.example.home.model.WeeklyTrendingMovies
import retrofit2.Response

interface TvShowsRepository {

    fun getAllPlaylists(): Flow<WeeklyTrendingMovies>

    fun searchQuery(query: String): Flow<Response<WeeklyTrendingMovies>>
}