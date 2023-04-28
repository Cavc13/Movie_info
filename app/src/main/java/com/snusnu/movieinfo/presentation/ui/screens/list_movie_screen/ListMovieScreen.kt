package com.snusnu.movieinfo.presentation.ui.screens.list_movie_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snusnu.movieinfo.R
import com.snusnu.movieinfo.domain.Movie
import com.snusnu.movieinfo.presentation.list_movie.ListMovieScreenState
import com.snusnu.movieinfo.presentation.list_movie.ListMovieViewModel

@Composable
fun HomeMovieScreen(
    viewModel: ListMovieViewModel,
    onCardClickListener: (Movie) -> Unit
) {
    val screenState = viewModel.screenState.observeAsState(ListMovieScreenState.Initial)

    when (screenState.value) {
        is ListMovieScreenState.MoviesPosts -> {
            ListMovieScreen(viewModel, onCardClickListener)
        }
        is ListMovieScreenState.Initial -> {

        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun ListMovieScreen(
    listMovieViewModel: ListMovieViewModel,
    onCardClickListener: (Movie) -> Unit
) {

    val movies = listMovieViewModel.movies.observeAsState(emptyList())


    Box {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.padding(
                    PaddingValues(
                        top = 56.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                )
            ) {
                TextField(
                    modifier = Modifier
                        .weight(1f),
                    value = listMovieViewModel.request,
                    onValueChange = listMovieViewModel::onRequestChange,
                    placeholder = {
                        Text(text = TEXT_SEARCH)
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )
                IconButton(onClick = { listMovieViewModel.searchTopMovies() }) {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        imageVector = Icons.Outlined.Search,
                        contentDescription = CONTENT_DESCRIPTION_ICON_SEARCH,
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
            CircularIndeterminateProgressBar(listMovieViewModel.isLoading)
            ForOffline(
                isDisplayed = listMovieViewModel.isOffline,
                listMovieViewModel = listMovieViewModel
            )
            NotFoundMovies(
                isDisplayed = listMovieViewModel.isNotFound,
                listMovieViewModel = listMovieViewModel
            )
            LazyColumn(
                contentPadding = PaddingValues(
                    top = 20.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(movies.value, key = { it.id }) { movie ->
                    val dismissState = rememberDismissState()
                    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                        listMovieViewModel.deleteMovie(movie.id)
                    }
                    SwipeToDismiss(
                        modifier = Modifier.animateItemPlacement(),
                        state = dismissState,
                        directions = setOf(DismissDirection.EndToStart),
                        background = {
                            Box(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .background(MaterialTheme.colors.primary)
                                    .fillMaxSize(),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Text(
                                    modifier = Modifier.padding(16.dp),
                                    text = TEXT_DELETE_MOVIE,
                                    color = Color.White,
                                    fontSize = 30.sp
                                )
                            }
                        }
                    ) {
                        CardMovie(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            movie = movie
                        ) {
                            onCardClickListener(movie)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CircularIndeterminateProgressBar(
    isDisplayed: Boolean
) {
    if (isDisplayed) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary
            )
        }
    }
}

@Composable
fun ForOffline(
    isDisplayed: Boolean,
    listMovieViewModel: ListMovieViewModel
) {
    if (isDisplayed) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(112.dp),
                painter = painterResource(id = R.drawable.ic_cloud_off),
                contentDescription = CONTENT_DESCRIPTION_OFFLINE,
                tint = MaterialTheme.colors.onSecondary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { listMovieViewModel.acceptedWarning() },
                shape = CircleShape
            ) {
                Text(text = TEXT_REPEAT)
            }
        }
    }
}

@Composable
fun NotFoundMovies(
    isDisplayed: Boolean,
    listMovieViewModel: ListMovieViewModel
) {
    if (isDisplayed) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(60.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { listMovieViewModel.acceptedWarning() },
                shape = CircleShape
            ) {
                Text(text = TEXT_NOT_FOUND)
            }
        }
    }
}

const val CONTENT_DESCRIPTION_ICON_SEARCH = "Искать фильм"
const val TEXT_SEARCH = "Поиск"
const val TEXT_DELETE_MOVIE = "Удалить фильм"
const val CONTENT_DESCRIPTION_OFFLINE = "Не в сети"
const val TEXT_NOT_FOUND = "Не найдено"
const val TEXT_REPEAT = "Повторить"