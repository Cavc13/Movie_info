package com.snusnu.movieinfo.domain

data class Movie(
    val id: Long,
    val name: String,
    val description: String,
    val genres: String,
    val countries: String,
    val year: String,
)
