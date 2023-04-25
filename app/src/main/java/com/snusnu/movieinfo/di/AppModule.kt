package com.snusnu.movieinfo.di

import android.app.Application
import com.snusnu.movieinfo.MovieDatabase
import com.snusnu.movieinfo.data.mapper.MovieMapper
import com.snusnu.movieinfo.data.source_impl.MovieDataSourceImpl
import com.snusnu.movieinfo.domain.MovieDataSource
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSqlDriver(app: Application): SqlDriver {
        return AndroidSqliteDriver(
            schema = MovieDatabase.Schema,
            context = app,
            name = "movie.db"
        )
    }

    @Provides
    @Singleton
    fun provideMovieDataSource(
        driver: SqlDriver,
        mapper: MovieMapper
    ): MovieDataSource {
        return MovieDataSourceImpl(
            MovieDatabase(driver),
            mapper
        )
    }
}