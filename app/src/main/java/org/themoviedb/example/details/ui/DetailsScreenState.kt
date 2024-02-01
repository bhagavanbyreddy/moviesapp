package org.themoviedb.example.details.ui

import org.themoviedb.example.details.model.DetailsModel
import org.themoviedb.example.home.model.WeeklyTrendingMovies

data class DetailsScreenState(
    var refreshing: Boolean = false,
    var isFavorite: Boolean = false,
    var errorMessage: String = "",
    var details: DetailsModel = DetailsModel(),
    var similarList : WeeklyTrendingMovies = WeeklyTrendingMovies(),
    var userToken: String = ""

)