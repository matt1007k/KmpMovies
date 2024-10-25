package com.maxdev.kmpmovies.data

class MoviesRepository(private val moviesService: MoviesService) {
    suspend fun fetchPopularMovies(): List<Movie> {
        return moviesService.fetchPopularMovies().results.map { it.toDomainMovie() }
    }
    suspend fun fetchMovieById(id: Int): Movie {
        return moviesService.fetchMovieById(id).toDomainMovie()
    }
}