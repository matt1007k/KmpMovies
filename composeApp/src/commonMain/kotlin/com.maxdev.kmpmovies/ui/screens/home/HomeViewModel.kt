package com.maxdev.kmpmovies.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxdev.kmpmovies.data.Movie
import com.maxdev.kmpmovies.data.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UiState(
    val loading: Boolean = false,
    val movies: List<Movie> = emptyList()
)

class HomeViewModel(
    private val moviesRepository: MoviesRepository
): ViewModel() {
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun getReady() {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            moviesRepository.movies.collect {
                if(it.isNotEmpty()){
                    _state.value = UiState(
                        loading = false,
                        movies = it
                    )
                }
            }
        }
    }

}