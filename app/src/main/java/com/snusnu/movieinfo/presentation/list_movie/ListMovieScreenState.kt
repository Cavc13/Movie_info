package com.snusnu.movieinfo.presentation.list_movie

import com.snusnu.movieinfo.domain.Movie

sealed class ListMovieScreenState {

    object Initial: ListMovieScreenState()

    data class MoviesPosts(val movies: List<Movie>) : ListMovieScreenState()
}
