package com.example.songkpop_compose

import androidx.annotation.DrawableRes

data class Album(
    val id: String,
    val title: String,
    val artist: String,
    val year: String,
    val description: String,
    @DrawableRes val imageResId: Int,
    val url: String
)