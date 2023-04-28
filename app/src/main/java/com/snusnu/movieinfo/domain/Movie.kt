package com.snusnu.movieinfo.domain

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Long,
    val name: String,
    val poster: String,
    val description: String,
    val genres: String,
    val countries: String,
    val year: String,
): Parcelable {

    companion object {
        val NavigationType: NavType<Movie> = object : NavType<Movie>(false) {
            override fun get(bundle: Bundle, key: String): Movie? {
                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getParcelable(key, Movie::class.java)
                } else {
                   bundle.getParcelable(key)
                }
            }

            override fun parseValue(value: String): Movie {
                return Gson().fromJson(value, Movie::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: Movie) {
                bundle.putParcelable(key, value)
            }

        }
    }
}
