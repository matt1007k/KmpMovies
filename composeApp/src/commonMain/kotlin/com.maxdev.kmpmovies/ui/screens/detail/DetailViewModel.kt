package com.maxdev.kmpmovies.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxdev.kmpmovies.data.Movie
import com.maxdev.kmpmovies.data.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val id: Int,
    private val repository: MoviesRepository
): ViewModel() {
    private var _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            repository.fetchMovieById(id).collect {
                it?.let {
                    _state.value = UiState(loading = false, movie = it)
                }
            }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movie: Movie? = null,
    )

    fun onFavoriteClick() {
        _state.value.movie?.let {
            viewModelScope.launch {
                repository.toggleFavorite(it)
            }
        }
    }
}