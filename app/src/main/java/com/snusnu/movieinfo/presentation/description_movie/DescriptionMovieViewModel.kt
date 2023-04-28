package com.snusnu.movieinfo.presentation.description_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snusnu.movieinfo.domain.MovieDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DescriptionMovieViewModel @Inject constructor(
    private val movieDataSource: MovieDataSource
): ViewModel() {

    private val _screenState = MutableLiveData<DescriptionScreenState>(DescriptionScreenState.Initial)
    val screenState: LiveData<DescriptionScreenState> = _screenState

    fun showDescriptionMovie(movieId: Long) {
        viewModelScope.launch {
            val movie = movieDataSource.getMovieById(movieId)
            movie?.let {
                _screenState.value = DescriptionScreenState.DescriptionMovie(movie)
            }
        }
    }
}