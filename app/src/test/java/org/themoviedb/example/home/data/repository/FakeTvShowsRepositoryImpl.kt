package org.themoviedb.example.home.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.themoviedb.example.home.model.Results
import org.themoviedb.example.home.model.WeeklyTrendingMovies
import org.themoviedb.example.home.repository.TvShowsRepository
import retrofit2.Response

class FakeTvShowsRepositoryImpl: TvShowsRepository {

    private val trendingMovies = WeeklyTrendingMovies()

    override fun getAllPlaylists(): Flow<WeeklyTrendingMovies> {
        return flow {
            emit(trendingMovies)
        }
    }

    fun insertTrendingMovies(inputResults: ArrayList<Results>){
        trendingMovies.results = inputResults
    }


    override fun searchQuery(query: String): Flow<Response<WeeklyTrendingMovies>> {
        TODO("Not yet implemented")
    }
}