package com.example.songkpop_compose.feature.domain.usecase

import com.example.songkpop_compose.feature.domain.repository.MoviePreferencesRepository

class SaveLastOpenedMovieTitleUseCase(private val preferencesRepository: MoviePreferencesRepository) {
    operator fun invoke(title: String) {
        preferencesRepository.saveLastOpenedMovieTitle(title)
    }
}