package com.example.songkpop_compose.feature.domain.repository

import com.example.songkpop_compose.core.common.UiState
import com.example.songkpop_compose.feature.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<List<Movie>>
    fun getMovieById(id: Int): Flow<Movie?> // Ditambahkan untuk DetailScreen
    fun fetchAndCacheMovies(): Flow<UiState<Unit>>
}