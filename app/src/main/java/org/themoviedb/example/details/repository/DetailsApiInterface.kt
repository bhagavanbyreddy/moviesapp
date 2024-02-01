package org.themoviedb.example.details.repository

import org.themoviedb.example.details.model.DetailsModel
import org.themoviedb.example.home.model.WeeklyTrendingMovies
import org.themoviedb.example.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailsApiInterface {

    //https://api.themoviedb.org/3/tv/239770?language=en-US&api_key=02e03f72eb926cb934c9cd5c310627ad
    @GET("tv/{id}")
    suspend fun getDetails(
        @Path("id")  id : Int,
        @Header("accept")  accept : String,
        @Query("language") language : String? = "en-US",
        @Query("api_key") apiKey: String? = Constants.API_KEY
    ): Response<DetailsModel>

    //https://api.themoviedb.org/3/tv/239770/similar?language=en-US&page=1&api_key=02e03f72eb926cb934c9cd5c310627ad
    @GET("tv/{id}/similar")
    suspend fun getSimilarLists(
        @Path("id")  id : Int,
        @Header("accept")  accept : String,
        @Query("language") language : String? = "en-US",
        @Query("page") page : Int = 1,
        @Query("api_key") apiKey: String? = Constants.API_KEY

    ): Response<WeeklyTrendingMovies>
}