package com.example.songkpop_compose.feature.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.songkpop_compose.core.common.UiState
import com.example.songkpop_compose.feature.domain.model.Movie
import com.example.songkpop_compose.feature.domain.usecase.GetLastOpenedMovieTitleUseCase
import com.example.songkpop_compose.feature.domain.usecase.GetMovieByIdUseCase
import com.example.songkpop_compose.feature.domain.usecase.GetPopularMoviesUseCase
import com.example.songkpop_compose.feature.domain.usecase.SaveLastOpenedMovieTitleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val getLastOpenedMovieTitleUseCase: GetLastOpenedMovieTitleUseCase,
    private val saveLastOpenedMovieTitleUseCase: SaveLastOpenedMovieTitleUseCase,
    private val categoryName: String
) : ViewModel() {

    private val _networkState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val networkState: StateFlow<UiState<Unit>> = _networkState.asStateFlow()
    val moviesList: StateFlow<List<Movie>> = getPopularMoviesUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _lastOpenedMovieTitle = MutableStateFlow<String?>(null)
    val lastOpenedMovieTitle: StateFlow<String?> = _lastOpenedMovieTitle.asStateFlow()

    init {
        Timber.i("MovieViewModel diinisialisasi untuk kategori: $categoryName")
        refreshMovies()
        loadLastOpenedMovieTitle()
    }

    fun refreshMovies() {
        viewModelScope.launch {
            getPopularMoviesUseCase.refresh().collect { state ->
                _networkState.value = state
            }
        }
    }

    fun getMovieById(id: Int): StateFlow<Movie?> {
        return getMovieByIdUseCase(id)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    }

    fun saveLastOpenedMovie(title: String) {
        viewModelScope.launch {
            saveLastOpenedMovieTitleUseCase(title)
            _lastOpenedMovieTitle.value = title
            Timber.i("Judul film terakhir berhasil disimpan ke Preferences: $title")
        }
    }

    private fun loadLastOpenedMovieTitle() {
        _lastOpenedMovieTitle.value = getLastOpenedMovieTitleUseCase()
    }
}