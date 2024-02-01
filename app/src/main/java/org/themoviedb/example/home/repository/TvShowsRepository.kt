package org.themoviedb.example.home.repository

import android.content.ContentValues.TAG
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.themoviedb.example.home.model.WeeklyTrendingMovies
import retrofit2.Response
import javax.inject.Inject

class TvShowsRepository @Inject constructor(
    private val apiInterface: TvShowsApiInterface,
) {

    suspend fun getAllPlaylists(): Flow<Response<WeeklyTrendingMovies>> {

        Log.d(TAG, "getAllPlaylists: is called")

        return fetchAllPlaylists()

    }

    private suspend fun fetchAllPlaylists(): Flow<Response<WeeklyTrendingMovies>> {
        return flow {
            val response = apiInterface.getTrendingLists(
                accept = "application/json"
            )
            Log.d(TAG, "fetchAllPlaylists: ${response.body()}")
            emit(response)
        }
    }

    suspend fun searchQuery(query: String): Flow<Response<WeeklyTrendingMovies>> {
        return flow {
            val response = apiInterface.getSearchLists(
                query = query,
                accept = "application/json"
            )
            Log.d(TAG, "fetchAllPlaylists: ${response.body()}")
            emit(response)
        }
    }

}