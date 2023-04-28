package com.snusnu.movieinfo.presentation.ui.screens.list_movie_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.snusnu.movieinfo.domain.Movie

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardMovie(
    modifier: Modifier = Modifier,
    movie: Movie,
    onCardItemClickListener: (Movie) -> Unit
) {
    Card(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.background,
        shape = RoundedCornerShape(8),
        elevation = 8.dp,
        onClick = {
            onCardItemClickListener(movie)
        }
        ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(50))
                    .padding(8.dp),
                model = movie.poster,
                contentDescription = CONTENT_DESCRIPTION_POSTER
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 48.dp),
            ) {
                Text(
                    text = movie.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${movie.genres.split(SPLIT_GENRE)[FIRST_GENRE]} (${movie.year})",
                    fontSize = 16.sp
                )
            }
        }
    }
}

const val CONTENT_DESCRIPTION_POSTER = "постер фильма"
const val SPLIT_GENRE = ","
const val FIRST_GENRE = 0