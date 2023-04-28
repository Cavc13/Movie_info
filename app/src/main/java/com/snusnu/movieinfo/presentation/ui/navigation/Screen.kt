package com.snusnu.movieinfo.presentation.ui.navigation

import android.net.Uri
import com.google.gson.Gson
import com.snusnu.movieinfo.domain.Movie

sealed class Screen(
    val route: String
) {

    object ListMovie: Screen(ROUTE_LIST_MOVIE)
    object HomeScreen: Screen(ROUTE_HOME_SCREEN)
    object DescriptionMovie : Screen(ROUTE_DESCRIPTION_MOVIE) {
        const val ROUTE_FOR_ARGS = "description"
    }

    companion object {
        const val KEY_MOVIE = "movie"

        const val ROUTE_HOME_SCREEN = "home"
        const val ROUTE_LIST_MOVIE = "list_movie"
        const val ROUTE_DESCRIPTION_MOVIE = "description_movie"
    }
}