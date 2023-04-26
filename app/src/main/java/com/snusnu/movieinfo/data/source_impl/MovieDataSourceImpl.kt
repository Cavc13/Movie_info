package com.snusnu.movieinfo.data.source_impl

import com.snusnu.movieinfo.MovieDatabase
import com.snusnu.movieinfo.data.DataConst
import com.snusnu.movieinfo.data.mapper.MovieMapper
import com.snusnu.movieinfo.data.network.MovieService
import com.snusnu.movieinfo.data.network.dto.Response
import com.snusnu.movieinfo.domain.Movie
import com.snusnu.movieinfo.domain.MovieDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(
    db: MovieDatabase,
    private val mapper: MovieMapper,
    private val client: HttpClient
) : MovieDataSource, MovieService {

    private val queries = db.movieEntityQueries

    override suspend fun getMovieById(id: Long): Movie? {
        return withContext(Dispatchers.IO) {
            val movieEntity = queries.getMovieById(id).executeAsOneOrNull()
            if (movieEntity != null) {
                mapper.mapMovieEntityToMovie(
                    movieEntity
                )
            } else {
                null
            }
        }
    }

    override suspend fun getMovieByName(name: String): List<Movie?> {
        return withContext(Dispatchers.IO) {
            val listMovieEntity = queries.getMovieByName(name).executeAsList()
            if (listMovieEntity.isNotEmpty()) {
                mapper.mapListMovieEntityToListMovie(listMovieEntity)
            } else {
                val networkResponse = getMoviesFromNetwork(DataConst.COUNT_OF_PAGE, name)
                if (networkResponse != null) {
                    val listEntity = mapper.mapResponseToListMovieEntity(networkResponse)
                    listEntity.forEach {
                        insertMovie(
                            it.id,
                            it.name,
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
    }

    override suspend fun deleteMovieById(id: Long) {
        withContext(Dispatchers.IO) {
            queries.deletMovieById(id)
        }
    }

    override suspend fun insertMovie(
        id: Long,
        name: String,
        description: String,
        genres: String,
        countries: String,
        year: String,
        lastSync: String
    ) {
        withContext(Dispatchers.IO) {
            queries.insertMovie(
                id,
                name,
                description,
                genres,
                countries,
                year,
                lastSync
            )
        }
    }

    override suspend fun getMoviesFromNetwork(page: String, keyword: String): Response? {
        return try {
            client.get{
                url{DataConst.API_GET_MOVIES_BY_NAME}
                parameter(DataConst.PARAM_PAGE, page)
                parameter(DataConst.PARAM_KEYWORD, keyword)
                header(DataConst.HEADERS_X_API_KEY, DataConst.X_API_KEY)
                header(DataConst.HEADERS_CONTENT_TYPE, DataConst.CONTENT_TYPE)
            }
        } catch (e: Exception) {
            null
        }
    }
}