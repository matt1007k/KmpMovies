package com.maxdev.kmpmovies.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxdev.kmpmovies.data.Movie
import com.maxdev.kmpmovies.data.MoviesRepository
import kotlinx.coroutines.launch

data class UiState(
    val loading: Boolean = false,
    val movies: List<Movie> = emptyList()
)

class HomeViewModel(
    private val moviesRepository: MoviesRepository
): ViewModel() {
    var state by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            state = UiState(loading = true)
            state = UiState(
                loading = false,
                movies = moviesRepository.fetchPopularMovies()
            )
        }
    }

}