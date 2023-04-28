package com.snusnu.movieinfo.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.snusnu.movieinfo.presentation.list_movie.ListMovieViewModel
import com.snusnu.movieinfo.presentation.ui.navigation.AppNavGraph
import com.snusnu.movieinfo.presentation.ui.navigation.rememberNavigationState
import com.snusnu.movieinfo.presentation.ui.theme.MovieInfoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val listMovieViewModel by viewModels<ListMovieViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieInfoTheme {
                val navigationState = rememberNavigationState()

                AppNavGraph(
                    listMovieViewModel = listMovieViewModel,
                    navHostController = navigationState.navHostController
                )
            }
        }
    }
}