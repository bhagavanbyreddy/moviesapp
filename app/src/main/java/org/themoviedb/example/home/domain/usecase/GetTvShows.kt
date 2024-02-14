package org.themoviedb.example.home.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import org.themoviedb.example.home.model.WeeklyTrendingMovies
import org.themoviedb.example.home.repository.TvShowsRepository
import org.themoviedb.example.home.repository.TvShowsRepositoryImpl
import javax.inject.Inject

class GetTvShows @Inject constructor(
    private val repository: TvShowsRepository
) {
    fun execute(): Flow<WeeklyTrendingMovies> {
        return flow {
            repository.getAllPlaylists().collect{ response ->
                emit(response)
            }
        }
    }
}