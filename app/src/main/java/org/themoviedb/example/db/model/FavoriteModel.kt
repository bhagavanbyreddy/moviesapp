package org.themoviedb.example.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.themoviedb.example.util.Constants.Companion.FAVORITE

@Entity(tableName = FAVORITE)
data class FavoriteModel(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @SerializedName("id") val showId: Int? = null,


)