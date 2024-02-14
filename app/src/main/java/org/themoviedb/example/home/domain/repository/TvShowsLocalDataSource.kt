package org.themoviedb.example.home.domain.repository

import kotlinx.coroutines.flow.Flow
import org.themoviedb.example.db.model.FavoriteModel

interface TvShowsLocalDataSource {

    suspend fun getAllFavorites(): List<FavoriteModel>

    suspend fun insertFavorite(favorite: FavoriteModel)

    suspend fun deleteFavorite(favorite: FavoriteModel)

}