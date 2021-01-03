package ru.s1mple.myapp.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface MoviesDataSource {
    suspend fun getMovies(): List<Movie>
    suspend fun getMovieById(movieId: Int): Movie?
}

class MoviesDataSourceImpl(
    val context: Context
) : MoviesDataSource {

    override suspend fun getMovies(): List<Movie> =
        withContext(Dispatchers.IO) {
            loadMovies(context).toMutableList()
        }

    override suspend fun getMovieById(movieId: Int): Movie? =
        withContext(Dispatchers.IO) {
            loadMovies(context).toMutableList().find { movieId == it.id }
        }
}