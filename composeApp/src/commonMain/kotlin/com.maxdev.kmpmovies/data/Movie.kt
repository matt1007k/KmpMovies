package com.maxdev.kmpmovies.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val poster: String,
    val backdrop: String? = "",
    val originalTitle: String,
    val originalLanguage: String,
    val popularity: Double,
    val voteAverage: Double,
    val isFavorite: Boolean
)

val movies = (1..100).map {
    Movie(
        id = it,
        title = "Movie $it",
        poster = "https://picsum.photos/200/300?id=$it",
        overview = "",
        releaseDate = "",
        backdrop = "",
        originalTitle = "",
        originalLanguage = "",
        popularity = 0.0,
        voteAverage = 0.0,
        isFavorite = false
    )
}