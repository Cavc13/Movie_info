package com.snusnu.movieinfo.presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snusnu.movieinfo.domain.Movie
import com.snusnu.movieinfo.domain.MovieDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVieModel @Inject constructor(
    private val movieDataSource: MovieDataSource
): ViewModel() {

    var movies by mutableStateOf<List<Movie>>(emptyList())
        private set

    var movieDetails by mutableStateOf<Movie?>(null)
        private set

    var request by mutableStateOf("")
        private set

    fun searchTopMovies() {

    }

    fun getMovie(id: Long) {
        viewModelScope.launch {
            movieDetails = movieDataSource.getMovieById(id)
        }
    }

    fun onRequestChange(text: String) {
        request = text
    }
}