package com.example.songkpop_compose.feature.data.repository

import android.content.Context
import com.example.songkpop_compose.feature.domain.repository.MoviePreferencesRepository

class MoviePreferencesRepositoryImpl(context: Context) : MoviePreferencesRepository {
    private val sharedPreferences = context.getSharedPreferences("movie_prefs", Context.MODE_PRIVATE)

    override fun saveLastOpenedMovieTitle(title: String) {
        sharedPreferences.edit().putString("last_opened_title", title).apply()
    }

    override fun getLastOpenedMovieTitle(): String? {
        return sharedPreferences.getString("last_opened_title", null)
    }
}