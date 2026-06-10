package com.example.songkpop_compose.feature.domain.usecase

import com.example.songkpop_compose.feature.domain.model.Movie
import com.example.songkpop_compose.feature.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetMovieByIdUseCase(private val repository: MovieRepository) {
    operator fun invoke(id: Int): Flow<Movie?> {
        return repository.getMovieById(id)
    }
}