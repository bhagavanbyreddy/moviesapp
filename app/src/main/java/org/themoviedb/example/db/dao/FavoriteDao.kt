package org.themoviedb.example.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.themoviedb.example.db.model.FavoriteModel
import org.themoviedb.example.util.Constants.Companion.FAVORITE


@Dao
interface FavoriteDao {
//    @Query("SELECT (SELECT COUNT(*) from $FAVORITE) == 0")
//    suspend fun isEmpty(): Boolean
//
//    @Query("SELECT * from $FAVORITE")
//    suspend fun getFavorite(): FavoriteModel
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun addFavoriteModel(languagesData: FavoriteModel)
//
//    @Query("DELETE FROM $FAVORITE")
//    suspend fun deleteFavoriteModel()

    @Insert
    fun insertFavorite(favorite: FavoriteModel)

    @Delete
    fun deleteFavorite(favorite: FavoriteModel)

    @Query("SELECT * FROM $FAVORITE")
    fun getAllFavorites(): List<FavoriteModel>

}