package org.themoviedb.example.details.repository

import android.content.ContentValues.TAG
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.themoviedb.example.details.model.DetailsModel
import org.themoviedb.example.home.model.WeeklyTrendingMovies
import retrofit2.Response
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val apiInterface: DetailsApiInterface,
) {

    suspend fun getDetails(id: String): Flow<Response<DetailsModel>> {

        Log.d(TAG, "getAllPlaylists: is called")

        return fetchDetails(id = id)

    }

    private suspend fun fetchDetails(id: String): Flow<Response<DetailsModel>> {
        return flow {
            val response = apiInterface.getDetails(
                // url = url,https://api.themoviedb.org/3/trending/all/week?language=en-US
                //accessToken = "Bearer 9457bf8ff9ecc3c20f89321525af0605",
                accept = "application/json",
                id = id.toInt()

            )
            Log.d(TAG, "fetchAllPlaylists: ${response.body()}")
            emit(response)
        }
    }

    suspend fun getSimilarList(id: String): Flow<Response<WeeklyTrendingMovies>> {
       return similarList(id = id)
    }

    private suspend fun similarList(id: String): Flow<Response<WeeklyTrendingMovies>> {
        return flow {
            val response = apiInterface.getSimilarLists(
                accept = "application/json",
                id = id.toInt()
            )
            Log.d(TAG, "fetchAllPlaylists: ${response.body()}")
            emit(response)
        }
    }

}