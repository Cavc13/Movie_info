package com.snusnu.movieinfo.presentation.description_movie

import com.snusnu.movieinfo.domain.Movie

sealed class DescriptionScreenState {
    object Initial: DescriptionScreenState()

    data class DescriptionMovie(val movie: Movie): DescriptionScreenState()
}
