package com.snusnu.movieinfo.data.source_impl

import com.snusnu.movieinfo.MovieDatabase
import com.snusnu.movieinfo.data.mapper.MovieMapper
import com.snusnu.movieinfo.domain.Movie
import com.snusnu.movieinfo.domain.MovieDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(
    db: MovieDatabase,
    private val mapper: MovieMapper
) : MovieDataSource {

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
                emptyList()
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
}