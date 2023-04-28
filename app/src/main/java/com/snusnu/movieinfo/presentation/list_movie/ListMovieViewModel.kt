package com.snusnu.movieinfo.presentation.list_movie

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snusnu.movieinfo.domain.Movie
import com.snusnu.movieinfo.domain.MovieDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListMovieViewModel @Inject constructor(
    private val movieDataSource: MovieDataSource,
    private val application: Application
) : ViewModel() {

    private val initialState = ListMovieScreenState.MoviesPosts(emptyList())
    var screenState = MutableLiveData<ListMovieScreenState>(initialState)
        private set

    private val _movies = MutableLiveData<List<Movie>>(emptyList())
    val movies: LiveData<List<Movie>> = _movies

    var request by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    var isOffline by mutableStateOf(false)
        private set

    var isNotFound by mutableStateOf(false)
        private set


    fun searchTopMovies() {
        if (request.isBlank()) return

        if (isOnline()) {
            viewModelScope.launch{
                isLoading = true
                val list = movieDataSource.getMovieByName(request.trim())
                if (list.isEmpty()) isNotFound = true else _movies.value = list
                request = ""
                isLoading = false
            }
        } else {
            isOffline = true
        }
    }

    fun deleteMovie(id: Long) {
        viewModelScope.launch {
            movieDataSource.deleteMovieById(id)
        }
    }

    fun onRequestChange(text: String) {
        request = text
    }

    private fun isOnline(): Boolean {
        val connectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(
                connectivityManager.activeNetwork
            ) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            if (connectivityManager.activeNetworkInfo != null &&
                connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting
            ) {
                return true
            }
        }
        return false
    }

    fun acceptedWarning() {
        isOffline = false
        isNotFound = false
    }
}