package com.snusnu.movieinfo.data.mapper

import com.snusnu.movieinfo.data.DataConst.EMPTY_TEXT
import com.snusnu.movieinfo.data.DataConst.PREFIX_LIST
import com.snusnu.movieinfo.data.DataConst.SUFFIX_LIST
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
            movieEntity.poster ?: EMPTY_TEXT,
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
                    .removePrefix(PREFIX_LIST)
                    .removeSuffix(SUFFIX_LIST),
                it.countries.map { nameCountry ->
                    nameCountry.country
                }
                    .toString()
                    .removePrefix(PREFIX_LIST)
                    .removeSuffix(SUFFIX_LIST),
                it.year,
                getCurrentDate()
            )
        }
    }
}