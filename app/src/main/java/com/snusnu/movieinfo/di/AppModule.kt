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
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import kotlinx.serialization.json.Json
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
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer(
                    Json {
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )

            }
        }
    }

    @Provides
    @Singleton
    fun provideMovieDataSource(
        driver: SqlDriver,
        mapper: MovieMapper,
        client: HttpClient
    ): MovieDataSource {
        return MovieDataSourceImpl(
            MovieDatabase(driver),
            mapper,
            client
        )
    }
}