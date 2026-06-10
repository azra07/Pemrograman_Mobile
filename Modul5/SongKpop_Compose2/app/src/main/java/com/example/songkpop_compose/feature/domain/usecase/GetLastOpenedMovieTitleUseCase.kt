package com.example.songkpop_compose.feature.domain.usecase

import com.example.songkpop_compose.feature.domain.repository.MoviePreferencesRepository

class GetLastOpenedMovieTitleUseCase(private val preferencesRepository: MoviePreferencesRepository) {
    operator fun invoke(): String? {
        return preferencesRepository.getLastOpenedMovieTitle()
    }
}