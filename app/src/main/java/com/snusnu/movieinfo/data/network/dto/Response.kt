package com.snusnu.movieinfo.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val films: List<Film>,
    val pagesCount: Int
)