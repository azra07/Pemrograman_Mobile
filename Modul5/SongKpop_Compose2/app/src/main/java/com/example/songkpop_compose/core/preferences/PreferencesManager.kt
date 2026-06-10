package com.example.songkpop_compose.core.preferences

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MovieAppPrefs", Context.MODE_PRIVATE)

    fun saveLastSearchQuery(query: String) {
        sharedPreferences.edit().putString("LAST_QUERY", query).apply()
    }

    fun getLastSearchQuery(): String {
        return sharedPreferences.getString("LAST_QUERY", "Popular") ?: "Popular"
    }
}