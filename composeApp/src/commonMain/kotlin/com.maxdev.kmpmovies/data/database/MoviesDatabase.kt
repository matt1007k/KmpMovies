@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package com.maxdev.kmpmovies.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.maxdev.kmpmovies.data.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

const val DATABASE_NAME = "movies.db"

@Database(entities = [Movie::class], version = 1)
@ConstructedBy(MoviesDatabaseConstructor::class)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}
//
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object MoviesDatabaseConstructor : RoomDatabaseConstructor<MoviesDatabase> {
    override fun initialize(): MoviesDatabase
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<MoviesDatabase>
): MoviesDatabase {
    return builder
//        .addMigrations(MIGRATIONS)
//        .setDriver(BundledSQLiteDriver())
//        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}