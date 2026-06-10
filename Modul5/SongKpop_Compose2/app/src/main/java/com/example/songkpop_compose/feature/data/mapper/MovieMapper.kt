package com.example.songkpop_compose.feature.data.mapper

import com.example.songkpop_compose.feature.data.local.MovieEntity
import com.example.songkpop_compose.feature.data.remote.dto.MovieDto
import com.example.songkpop_compose.feature.domain.model.Movie

fun MovieDto.toEntity(): MovieEntity {
    return MovieEntity(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterPath = this.posterPath ?: "",
        releaseDate = this.releaseDate ?: "",
        rating = this.voteAverage
    )
}

fun MovieEntity.toDomain(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        rating = this.rating
    )
}