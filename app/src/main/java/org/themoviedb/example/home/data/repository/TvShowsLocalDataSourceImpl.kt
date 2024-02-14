package org.themoviedb.example.home.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import org.themoviedb.example.db.dao.FavoriteDao
import org.themoviedb.example.db.model.FavoriteModel
import org.themoviedb.example.home.domain.repository.TvShowsLocalDataSource
import javax.inject.Inject

class TvShowsLocalDataSourceImpl @Inject constructor(
    private val dao: FavoriteDao
): TvShowsLocalDataSource {
    override suspend fun getAllFavorites(): List<FavoriteModel> {
        return dao.getAllFavorites()
    }

    override suspend fun insertFavorite(favorite: FavoriteModel) {
        dao.insertFavorite(favorite)
    }

    override suspend fun deleteFavorite(favorite: FavoriteModel) {
        dao.deleteFavorite(favorite)
    }


}