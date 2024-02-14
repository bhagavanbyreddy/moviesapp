package org.themoviedb.example.home.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.themoviedb.example.home.domain.repository.TvShowsLocalDataSource
import org.themoviedb.example.home.domain.usecase.GetTvShows
import org.themoviedb.example.home.model.Results
import org.themoviedb.example.home.repository.TvShowsRepository
import org.themoviedb.example.home.ui.HomeScreenState
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: GetTvShows,
    private val repository: TvShowsRepository,
    private val localDataSource: TvShowsLocalDataSource

    ) : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()
    private var textSearch = MutableStateFlow("")
    private var searchJob = SupervisorJob()
    private var getTvShowsJob: Job? = null

    init {
        _state.update {
            it.copy(refreshing = true)
        }
        searchQuery(q = "")
    }

    private fun topList() {
        Log.d("call1:::","true")
        //getTvShowsJob?.cancel()
        viewModelScope.launch(Dispatchers.IO) {
            useCase.execute().collect { tvShows ->
                tvShows.results.let { playLists ->
                    _state.update {
                        it.copy(
                            refreshing = false,
                            playlists = playLists
                        )
                    }

                }
            }
        }
    }


    fun searchQuery(q: String) {
        textSearch.value = q
        if (q.isNotEmpty()) {
            search()
        } else {
            topList()
        }
    }

    @OptIn(FlowPreview::class)
    private fun search() {
        viewModelScope.launch(Dispatchers.IO) {
            textSearch.debounce(500).collect {
                if (it.isNotEmpty())
                    searchContent(it)
            }
        }
    }

    private suspend fun searchContent(query: String) {
        if (searchJob.isActive)
            searchJob.cancel()
        searchJob = Job()
        if (query.length > 2) {
            viewModelScope.launch(Dispatchers.IO + searchJob) {
                val response = repository.searchQuery(query = query)
                response.cancellable().retry(3) { cause ->
                    if (cause is IOException) {
                        delay(1000)
                        return@retry true
                    } else {
                        return@retry false
                    }
                }.catch { e ->
                    Log.d("", e.toString())
                    _state.update {
                        it.copy(
                            playlists = arrayListOf(),
                            errorMessage = e.localizedMessage,
                        )
                    }
                }.collectLatest {

                    _state.update { data ->
                        data.copy(
                            errorMessage = "",
                            playlists = it.body()?.results ?: arrayListOf(),
                        )
                    }

                    val data = it.body()?.results?.map { result ->
                        var hasFav = false

                        localDataSource.getAllFavorites().forEach {db ->
                                if (db.showId == result.id ) {
                                    hasFav = true
                                }
                            }
                        result.isFavorite = hasFav
                        result.copy(isFavorite = hasFav)

                    }?.toList()

                    _state.update { sata->
                        sata.copy(
                            playlists = data as ArrayList<Results>
                        )
                    }


                }

            }
        } else {
            _state.update { state ->
                state.copy(
                    playlists = arrayListOf(),
                )
            }
        }
    }

}