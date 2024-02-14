package org.themoviedb.example.home.repository

import org.themoviedb.example.home.model.WeeklyTrendingMovies
import org.themoviedb.example.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TvShowsApiInterface {
    @GET("trending/tv/week")
    suspend fun getTrendingLists(
        @Header("accept") accept: String? = "application/json",
        @Query("language") language: String? = "en-US",
        @Query("api_key") apiKey: String? = Constants.API_KEY
    ): Response<WeeklyTrendingMovies>

    @GET("search/tv")
    suspend fun getSearchLists(
        @Header("accept") accept: String? = "application/json",
        @Query("query") query: String,
        @Query("include_adult") contentType: Boolean = false,
        @Query("language") language: String? = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String? = Constants.API_KEY

    ): Response<WeeklyTrendingMovies>
}