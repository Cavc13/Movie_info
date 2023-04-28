package com.snusnu.movieinfo.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.snusnu.movieinfo.domain.Movie
import com.snusnu.movieinfo.presentation.list_movie.ListMovieViewModel
import com.snusnu.movieinfo.presentation.ui.screens.description_screen.DescriptionScreen
import com.snusnu.movieinfo.presentation.ui.screens.list_movie_screen.HomeMovieScreen

@Composable
fun AppNavGraph(
    listMovieViewModel: ListMovieViewModel,
    navHostController: NavHostController
) {

    NavHost(
        navController = navHostController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(route = Screen.HomeScreen.route) {
            HomeMovieScreen(listMovieViewModel) {movie ->
                navHostController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set(Screen.DescriptionMovie.ROUTE_FOR_ARGS, movie)
                navHostController.navigate(Screen.DescriptionMovie.route)
            }
        }
        composable(route = Screen.DescriptionMovie.route){
            val movie = navHostController.previousBackStackEntry
                ?.savedStateHandle?.get<Movie>(Screen.DescriptionMovie.ROUTE_FOR_ARGS)
            movie?.let {
                DescriptionScreen(movie) {
                    navHostController.popBackStack()
                }
            }
        }
    }
}