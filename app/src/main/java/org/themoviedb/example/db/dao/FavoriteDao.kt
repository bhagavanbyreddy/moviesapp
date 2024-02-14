package org.themoviedb.example.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.themoviedb.example.db.model.FavoriteModel
import org.themoviedb.example.util.Constants.Companion.FAVORITE


@Dao
interface FavoriteDao {
    @Insert
    suspend fun insertFavorite(favorite: FavoriteModel)

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteModel)

    @Query("SELECT * FROM $FAVORITE")
    suspend fun getAllFavorites(): List<FavoriteModel>

}