package com.example.songkpop_compose.feature.domain.repository

interface MoviePreferencesRepository {
    fun saveLastOpenedMovieTitle(title: String)
    fun getLastOpenedMovieTitle(): String?
}