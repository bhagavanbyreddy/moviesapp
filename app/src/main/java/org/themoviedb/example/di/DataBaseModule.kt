package org.themoviedb.example.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.themoviedb.example.db.MovieDbDataBase
import org.themoviedb.example.db.dao.FavoriteDao
import org.themoviedb.example.util.Constants.Companion.MOVIE_DB_NAME
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataBaseModule {
    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext appContext: Context): MovieDbDataBase {
        return Room.databaseBuilder(
            appContext,
            MovieDbDataBase::class.java,
            MOVIE_DB_NAME
        ).fallbackToDestructiveMigrationFrom(6).build()
    }

    @Provides
    fun provideFavoriteDao(db: MovieDbDataBase): FavoriteDao {
        return db.getFavoriteDao()
    }
}