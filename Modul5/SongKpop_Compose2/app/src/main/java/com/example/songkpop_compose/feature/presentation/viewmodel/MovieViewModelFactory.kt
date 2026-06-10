package com.example.songkpop_compose.feature.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.songkpop_compose.feature.domain.usecase.GetLastOpenedMovieTitleUseCase
import com.example.songkpop_compose.feature.domain.usecase.GetMovieByIdUseCase
import com.example.songkpop_compose.feature.domain.usecase.GetPopularMoviesUseCase
import com.example.songkpop_compose.feature.domain.usecase.SaveLastOpenedMovieTitleUseCase

class MovieViewModelFactory(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val getLastOpenedMovieTitleUseCase: GetLastOpenedMovieTitleUseCase,
    private val saveLastOpenedMovieTitleUseCase: SaveLastOpenedMovieTitleUseCase,
    private val categoryName: String
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            return MovieViewModel(
                getPopularMoviesUseCase,
                getMovieByIdUseCase,
                getLastOpenedMovieTitleUseCase,
                saveLastOpenedMovieTitleUseCase,
                categoryName
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}