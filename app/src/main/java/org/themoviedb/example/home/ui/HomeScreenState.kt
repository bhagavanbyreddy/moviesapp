package org.themoviedb.example.home.ui

import org.themoviedb.example.home.model.Results
import org.themoviedb.example.home.model.WeeklyTrendingMovies

data class HomeScreenState(
    var refreshing: Boolean = false,
    var errorMessage: String = "",
    var playlists: ArrayList<Results> = arrayListOf(),
    var userToken: String = ""
)