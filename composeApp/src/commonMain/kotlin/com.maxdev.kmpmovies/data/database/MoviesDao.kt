package com.maxdev.kmpmovies.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maxdev.kmpmovies.data.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * FROM Movie")
    fun fetchPopularMovies(): Flow<List<Movie>>

    @Query("SELECT * FROM Movie WHERE id = :id")
    fun fetchMovieById(id: Int): Flow<Movie?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(movie: List<Movie>)

}