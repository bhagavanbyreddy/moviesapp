package org.themoviedb.example.home.repository

import android.content.ContentValues.TAG
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.themoviedb.example.db.model.FavoriteModel
import org.themoviedb.example.home.domain.repository.TvShowsLocalDataSource
import org.themoviedb.example.home.domain.repository.TvShowsRemoteDataSource
import org.themoviedb.example.home.model.WeeklyTrendingMovies
import retrofit2.Response
import javax.inject.Inject

class TvShowsRepositoryImpl @Inject constructor(
    private val remoteDataSource: TvShowsRemoteDataSource,
    private val localDataSource: TvShowsLocalDataSource
): TvShowsRepository {

    override fun getAllPlaylists(): Flow<WeeklyTrendingMovies> {
        return flow {
            val response = fetchAllPlaylists().body()
            response?.results?.map { playList ->
                var hasFav = false

                    fetchFavouritePlaylists().forEach { favPlayList ->
                        if (favPlayList.showId == playList.id) {
                            hasFav = true
                        }
                    }
                playList.isFavorite = hasFav
                playList.copy(isFavorite = hasFav)
            }
            response?.let { emit(it) }
        }
    }

    private suspend fun fetchAllPlaylists(): Response<WeeklyTrendingMovies> {
        return remoteDataSource.getTrendingLists()
    }

    private suspend fun fetchFavouritePlaylists(): List<FavoriteModel> {
        return localDataSource.getAllFavorites()
    }

    override fun searchQuery(query: String): Flow<Response<WeeklyTrendingMovies>> {
        return flow {
            emit(remoteDataSource.getSearchLists(
                query = query
            ))
        }

    }

}