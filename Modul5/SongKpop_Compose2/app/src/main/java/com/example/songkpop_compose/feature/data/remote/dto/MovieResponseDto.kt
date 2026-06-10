package com.example.songkpop_compose.feature.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponseDto(
    @SerialName("results") val results: List<MovieDto>
)