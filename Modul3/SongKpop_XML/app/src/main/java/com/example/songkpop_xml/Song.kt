package com.example.songkpop_xml

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song (
    val title: String,
    val artist: String,
    val image: Int,
    val years: String,
    val description: String,
    val youtubeUrl: String
): Parcelable