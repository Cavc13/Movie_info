package com.snusnu.movieinfo.domain

interface MovieDataSource {

    suspend fun getMovieById(id: Long): Movie?

    suspend fun getMovieByName(name: String): List<Movie?>

    suspend fun deleteMovieById(id: Long)

    suspend fun insertMovie(
        id: Long,
        name: String,
        description: String,
        genres: String,
        countries: String,
        year: String,
        lastSync: String
    )
}