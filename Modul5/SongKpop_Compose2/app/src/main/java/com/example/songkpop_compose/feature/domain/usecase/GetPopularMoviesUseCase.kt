package com.example.songkpop_compose.feature.domain.usecase

import com.example.songkpop_compose.core.common.UiState
import com.example.songkpop_compose.feature.domain.model.Movie
import com.example.songkpop_compose.feature.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesUseCase(private val repository: MovieRepository) {
    operator fun invoke(): Flow<List<Movie>> {
        return repository.getPopularMovies()
    }

    fun refresh(): Flow<UiState<Unit>> {
        return repository.fetchAndCacheMovies()
    }
}