package ru.s1mple.myapp.data

import MovieDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.s1mple.myapp.network.RetrofitModule

interface MoviesDataSource {
    suspend fun getMovies(): List<Movie>
    suspend fun getMovieById(mId: Long): MovieDetails
    suspend fun getActorsByMovieId(mId: Long): List<Actor>
    suspend fun setGenres()
}

/**
 * Класс отвечающий за получение и хранение данных из API
 */
object MoviesDataSourceImpl : MoviesDataSource {
    private var movies = emptyList<Movie>()
    private var genres = emptyList<Genre>()


    fun getGenresList(): List<Genre> = genres

    /**
     * Сохраняет в [movies] и возвращает список фильмов
     *
     * @return список фильмов
     */
    override suspend fun getMovies(): List<Movie> =
        withContext(Dispatchers.IO) {
            setGenres()
            if (movies.isEmpty()) movies = RetrofitModule.moviesApi.loadMovies().movies
            movies
        }

    /**
     * Возвращает объект фильма по id
     *
     * @param mId id фильма
     * @return объект фильма
     */
    override suspend fun getMovieById(mId: Long): MovieDetails =
        withContext(Dispatchers.IO) {
            RetrofitModule.moviesApi.loadMovie(mId)
        }


    /**
     * Возвращает список актеров фильма по id фильма
     *
     * @param mId id фильма
     * @return список актеров
     */
    override suspend fun getActorsByMovieId(mId: Long): List<Actor> =
        withContext(Dispatchers.IO) {
            RetrofitModule.moviesApi.loadActors(mId).cast.filter {
                it.profilePath != null
            }
        }

    /**
     * Возвращает список жанров
     *
     * @return список жанров
     */
    override suspend fun setGenres() =
        withContext(Dispatchers.IO) {
            if (genres.isEmpty()) genres = RetrofitModule.moviesApi.loadGenres().genres
        }

    fun toJSON(collection: Collection<Int>): String = collection.joinToString(", ", "[", "]")
}