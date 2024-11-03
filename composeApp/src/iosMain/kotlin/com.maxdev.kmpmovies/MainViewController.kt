package com.maxdev.kmpmovies

import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) {
//    val database: MoviesDatabase = getRoomDatabase(getDatabaseBuilder())
//    App(database.moviesDao())
    App()
}