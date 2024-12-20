package com.maxdev.kmpmovies.data

import com.maxdev.kmpmovies.data.database.MoviesDao
import com.maxdev.kmpmovies.data.remote.MoviesService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class MoviesRepository(
    private val moviesService: MoviesService,
    private val moviesDao: MoviesDao,
    private val regionRepository: RegionRepository
) {
    val movies: Flow<List<Movie>> = moviesDao.fetchPopularMovies().onEach { movies ->
        if(movies.isEmpty()) {
            val popularMovies = moviesService.fetchPopularMovies(
                regionRepository.fetchRegion()
            ).results.map { it.toDomainMovie() }
            moviesDao.save(popularMovies)
        }
    }
    suspend fun fetchMovieById(id: Int): Flow<Movie?> = moviesDao.fetchMovieById(id).onEach{ movie ->
        if(movie == null) {
            val remoteMovie = moviesService.fetchMovieById(id).toDomainMovie()
            moviesDao.save(listOf(remoteMovie))
        }
    }

    suspend fun toggleFavorite(movie: Movie) {
        moviesDao.save(listOf(movie.copy(isFavorite = !movie.isFavorite)))
    }
}