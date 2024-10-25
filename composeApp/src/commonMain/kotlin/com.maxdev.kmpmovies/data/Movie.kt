package com.maxdev.kmpmovies.data

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val poster: String,
    val backdrop: String?,
    val originalTitle: String,
    val originalLanguage: String,
    val popularity: Double,
    val voteAverage: Double,
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
        voteAverage = 0.0
    )
}