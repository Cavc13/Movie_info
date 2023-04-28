package com.snusnu.movieinfo.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.rememberNavController
import com.snusnu.movieinfo.presentation.list_movie.ListMovieScreenState
import com.snusnu.movieinfo.presentation.list_movie.ListMovieViewModel
import com.snusnu.movieinfo.presentation.ui.navigation.AppNavGraph
import com.snusnu.movieinfo.presentation.ui.navigation.Screen
import com.snusnu.movieinfo.presentation.ui.navigation.rememberNavigationState
import com.snusnu.movieinfo.presentation.ui.screens.description_screen.DescriptionScreen
import com.snusnu.movieinfo.presentation.ui.screens.list_movie_screen.HomeMovieScreen
import com.snusnu.movieinfo.presentation.ui.screens.list_movie_screen.ListMovieScreen

//@Composable
//fun MovieMainScreen() {
//
//    val navigationState = rememberNavigationState()
//
//    AppNavGraph(
//        navHostController = navigationState.navHostController
//    )
//}