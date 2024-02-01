package org.themoviedb.example.db

import androidx.room.Database
import androidx.room.RoomDatabase
import org.themoviedb.example.db.dao.FavoriteDao
import org.themoviedb.example.db.model.FavoriteModel


@Database(
    entities = [FavoriteModel::class],
    version = 1,
    exportSchema = true,
    autoMigrations = [
    ],
)
abstract class MovieDbDataBase : RoomDatabase() {
    abstract fun getFavoriteDao(): FavoriteDao


}