package org.themoviedb.example.details.viewModel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.themoviedb.example.db.dao.FavoriteDao
import org.themoviedb.example.db.model.FavoriteModel
import org.themoviedb.example.details.repository.DetailsRepository
import org.themoviedb.example.details.ui.DetailsScreenState
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: DetailsRepository,
    savedStateHandle: SavedStateHandle,
    private val favoriteDao: FavoriteDao

) : ViewModel() {
    private val _state = MutableStateFlow(DetailsScreenState())
    val state: StateFlow<DetailsScreenState> = _state.asStateFlow()

    init {
        val id: String = savedStateHandle["id"] ?: "0"
        details(id = id)


    }

    fun addToFavorite(id: Int) {
        var hasFav: Boolean = false;
        if (id > -1) {
            CoroutineScope(Dispatchers.IO).launch {
                    favoriteDao.getAllFavorites().forEach {
                        if (it.showId == id) {
                            hasFav = true
                        }
                    }
                if (!hasFav)
                    favoriteDao.insertFavorite(FavoriteModel(showId = id))
                _state.update {
                    it.copy(
                        isFavorite = !hasFav
                        //playlists = result.body()
                    )
                }
            }

        }

    }

    fun removeToFavorite(id: Int) {
        var hasFav: Boolean = false;
        var favId = -1;
        if (id > -1) {
            CoroutineScope(Dispatchers.IO).launch {
                    favoriteDao.getAllFavorites().forEach {
                        if (it.showId == id) {
                            hasFav = true
                            favId = it.id
                        }
                    }
                if (hasFav)
                    favoriteDao.deleteFavorite(FavoriteModel(showId = id, id = favId))

                _state.update {
                    it.copy(
                        isFavorite = !hasFav
                        //playlists = result.body()
                    )
                }
            }


        }

    }

    private fun details(id: String) {
        var hasFav: Boolean = false;

        CoroutineScope(Dispatchers.IO).launch {

            repository.getDetails(id = id).collect { result ->
                Log.d("TAG", "topList: ${result.body().toString()}")
                result.body()?.let { data ->
                    _state.update {
                        it.copy(
                            details = data,
                        )
                    }
                }
                favoriteDao.getAllFavorites().forEach {
                        if (it.showId == id.toInt()) {
                            hasFav = true
                        }
                    }

                _state.update {
                    it.copy(
                        isFavorite = hasFav
                    )
                }

            }
        }

        CoroutineScope(Dispatchers.IO).launch {

            repository.getSimilarList(id = id).collect { result ->
                Log.d("TAG", "topList: ${result.body().toString()}")
                result.body()?.let { data ->
                    _state.update {
                        it.copy(
                            similarList = data,
                        )
                    }
                }
                favoriteDao.getAllFavorites().forEach {
                        if (it.showId == id.toInt()) {
                            hasFav = true
                        }
                    }
                _state.update {
                    it.copy(
                        isFavorite = hasFav
                    )
                }

            }
        }
    }

}