package com.maxdev.kmpmovies.data

import com.maxdev.kmpmovies.data.database.MoviesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class MoviesRepository(private val moviesService: MoviesService, private val moviesDao: MoviesDao) {
    val movies: Flow<List<Movie>> = moviesDao.fetchPopularMovies().onEach { movies ->
        if(movies.isEmpty()) {
            val popularMovies = moviesService.fetchPopularMovies().results.map { it.toDomainMovie() }
            moviesDao.save(popularMovies)
        }
    }
    suspend fun fetchMovieById(id: Int): Flow<Movie?> = moviesDao.fetchMovieById(id).onEach{ movie ->
        if(movie == null) {
            val remoteMovie = moviesService.fetchMovieById(id).toDomainMovie()
            moviesDao.save(listOf(remoteMovie))
        }
    }
}