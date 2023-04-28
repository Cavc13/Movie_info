package com.snusnu.movieinfo.data.mapper

import android.util.Log
import com.snusnu.movieinfo.data.network.dto.Response
import com.snusnu.movieinfo.domain.Movie
import com.snusnu.movieinfo.utils.getCurrentDate
import movieinfo.moviedb.MovieEntity
import javax.inject.Inject

class MovieMapper @Inject constructor() {

    fun mapMovieEntityToMovie(movieEntity: MovieEntity): Movie {
        return Movie(
            movieEntity.id,
            movieEntity.name,
            movieEntity.poster ?: "",
            movieEntity.description,
            movieEntity.genres,
            movieEntity.countries,
            movieEntity.year
        )
    }

    fun mapListMovieEntityToListMovie(list: List<MovieEntity>): List<Movie> {
        return list.map {
            mapMovieEntityToMovie(it)
        }
    }

    fun mapResponseToListMovieEntity(response: Response): List<MovieEntity> {
        val listFilms = response.films
        return listFilms.map {
            MovieEntity(
                it.filmId.toLong(),
                it.nameRu,
                it.posterUrl,
                it.description,
                it.genres.map { nameGenre ->
                    nameGenre.genre
                }
                    .toString()
                    .removePrefix("[")
                    .removeSuffix("]"),
                it.countries.map { nameCountry ->
                    nameCountry.country
                }
                    .toString()
                    .removePrefix("[")
                    .removeSuffix("]"),
                it.year,
                getCurrentDate()
            )
        }
    }
}