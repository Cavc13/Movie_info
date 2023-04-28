package com.snusnu.movieinfo.presentation.ui.screens.description_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.Text
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.snusnu.movieinfo.domain.Movie

@Composable
fun DescriptionScreen(
    movie: Movie,
    onBackPressed: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            AsyncImage(
                modifier = Modifier.weight(1f),
                model = movie.poster,
                contentDescription = CONTENT_DESCRIPTION_POSTER,
                contentScale = ContentScale.FillWidth,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                Text(text = movie.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = movie.description)
                Spacer(modifier = Modifier.height(8.dp))
                DescriptionText(title= GENRE,text = movie.genres)
                DescriptionText(title= COUNTRIES,text = movie.countries)
                DescriptionText(title = YEAR, text = movie.year)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        IconButton(
            onClick = { onBackPressed() },
            modifier = Modifier
                .padding(top = 36.dp, start = 24.dp)
                .size(32.dp),
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = CONTENT_DESCRIPTION_BUTTON_BACK,
                tint = MaterialTheme.colors.onSecondary
            )
        }
    }
}

@Composable
fun DescriptionText(title: String, text: String) {
    Row {
        Text(text = title, fontWeight = FontWeight.Bold)
        Text(text = text)
    }
    Spacer(modifier = Modifier.height(8.dp))
}

const val CONTENT_DESCRIPTION_POSTER = "Постер фильма"
const val CONTENT_DESCRIPTION_BUTTON_BACK = "Назад"
const val GENRE = "Жанры: "
const val COUNTRIES = "Страны: "
const val YEAR = "Год: "