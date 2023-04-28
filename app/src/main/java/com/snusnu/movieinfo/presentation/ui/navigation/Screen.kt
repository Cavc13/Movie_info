package com.snusnu.movieinfo.presentation.ui.navigation

sealed class Screen(
    val route: String
) {
    object HomeScreen: Screen(ROUTE_HOME_SCREEN)
    object DescriptionMovie : Screen(ROUTE_DESCRIPTION_MOVIE) {
        const val ROUTE_FOR_ARGS = "description"
    }

    companion object {
        const val ROUTE_HOME_SCREEN = "home"
        const val ROUTE_DESCRIPTION_MOVIE = "description_movie"
    }
}