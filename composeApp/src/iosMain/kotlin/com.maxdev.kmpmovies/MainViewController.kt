package com.maxdev.kmpmovies

import androidx.compose.ui.window.ComposeUIViewController
import com.maxdev.kmpmovies.data.database.MoviesDatabase
import com.maxdev.kmpmovies.data.database.getDatabaseBuilder
import com.maxdev.kmpmovies.data.database.getRoomDatabase

fun MainViewController() = ComposeUIViewController {
    val database: MoviesDatabase = getRoomDatabase(getDatabaseBuilder())
    App(database.moviesDao())
}

//[ksp]  @Database class must be annotated with @ConstructedBy since the source is targeting non-Android platforms.
