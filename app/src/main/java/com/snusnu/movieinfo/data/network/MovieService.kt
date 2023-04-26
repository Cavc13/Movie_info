package com.snusnu.movieinfo.data.network

import com.snusnu.movieinfo.data.network.dto.Response

interface MovieService {

    suspend fun getMoviesFromNetwork(page: String, keyword: String): Response?
}