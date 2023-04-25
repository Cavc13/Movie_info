package com.snusnu.movieinfo.data.mapper

import com.snusnu.movieinfo.domain.Movie
import movieinfo.moviedb.MovieEntity
import javax.inject.Inject

class MovieMapper @Inject constructor(){

    fun mapMovieEntityToMovie(movieEntity: MovieEntity) : Movie {
        return Movie(
            movieEntity.id,
            movieEntity.name,
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
}