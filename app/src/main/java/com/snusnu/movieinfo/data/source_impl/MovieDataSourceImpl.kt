package com.snusnu.movieinfo.data.source_impl

import com.snusnu.movieinfo.MovieDatabase
import com.snusnu.movieinfo.data.DataConst
import com.snusnu.movieinfo.data.DataConst.API_GET_MOVIES_BY_KEYWORD
import com.snusnu.movieinfo.data.DataConst.CONTENT_TYPE
import com.snusnu.movieinfo.data.DataConst.EMPTY_TEXT
import com.snusnu.movieinfo.data.DataConst.FIRST_EXAMPLE_MOVIE
import com.snusnu.movieinfo.data.DataConst.HEADERS_CONTENT_TYPE
import com.snusnu.movieinfo.data.DataConst.HEADERS_X_API_KEY
import com.snusnu.movieinfo.data.DataConst.PARAM_KEYWORD
import com.snusnu.movieinfo.data.DataConst.PARAM_PAGE
import com.snusnu.movieinfo.data.DataConst.X_API_KEY
import com.snusnu.movieinfo.data.mapper.MovieMapper
import com.snusnu.movieinfo.data.network.MovieService
import com.snusnu.movieinfo.data.network.dto.Response
import com.snusnu.movieinfo.di.IoDispatcher
import com.snusnu.movieinfo.domain.Movie
import com.snusnu.movieinfo.domain.MovieDataSource
import com.snusnu.movieinfo.utils.getCurrentDate
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(
    db: MovieDatabase,
    private val mapper: MovieMapper,
    private val client: HttpClient,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : MovieDataSource, MovieService {
    private val queries = db.movieEntityQueries

    override suspend fun getMovieByName(name: String): List<Movie> =
        withContext(ioDispatcher) {
            val listMovieEntity = queries.getMovieByName(name).executeAsList()

            if (
                listMovieEntity.isNotEmpty()
                && listMovieEntity[FIRST_EXAMPLE_MOVIE].lastSync == getCurrentDate()
            ) {
                mapper.mapListMovieEntityToListMovie(listMovieEntity)
            } else {
                val networkResponse = getMoviesFromNetwork(DataConst.COUNT_OF_PAGE, name)
                if (networkResponse != null) {
                    val listEntity = mapper.mapResponseToListMovieEntity(networkResponse)
                    listEntity.forEach {
                        insertMovie(
                            it.id,
                            it.name,
                            it.poster ?: EMPTY_TEXT,
                            it.description,
                            it.genres,
                            it.countries,
                            it.year,
                            it.lastSync
                        )
                    }
                    listEntity.map {
                        mapper.mapMovieEntityToMovie(it)
                    }
                } else {
                    emptyList()
                }
            }
        }

    override suspend fun deleteMovieById(id: Long) = withContext(ioDispatcher) {
        queries.deletMovieById(id)
    }


    override suspend fun insertMovie(
        id: Long,
        name: String,
        poster: String,
        description: String,
        genres: String,
        countries: String,
        year: String,
        lastSync: String
    ) = withContext(ioDispatcher) {
        queries.insertMovie(
            id,
            name,
            poster,
            description,
            genres,
            countries,
            year,
            lastSync
        )
    }

    override suspend fun getMoviesFromNetwork(page: String, keyword: String): Response? {
        return try {
            client.get {
                url(API_GET_MOVIES_BY_KEYWORD)
                header(HEADERS_X_API_KEY, X_API_KEY)
                header(HEADERS_CONTENT_TYPE, CONTENT_TYPE)
                parameter(PARAM_PAGE, page)
                parameter(PARAM_KEYWORD, keyword)
            }
        } catch (e: Exception) {
            null
        }
    }
}