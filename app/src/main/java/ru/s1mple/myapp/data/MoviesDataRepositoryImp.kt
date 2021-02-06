package ru.s1mple.myapp.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.s1mple.myapp.database.MovieDetailsEntity
import ru.s1mple.myapp.database.MovieEntity
import ru.s1mple.myapp.database.MoviesLocalDataBase
import ru.s1mple.myapp.network.RetrofitModule

interface MoviesDataRepository {
    suspend fun getMovies(needRefresh: Boolean): List<Movie>
    suspend fun getMovieById(mId: Long): MovieDetails
    suspend fun getActorsByMovieId(mId: Long): List<Actor>
    suspend fun loadGenres()
}

/**
 * Класс отвечающий за получение и хранение данных из API
 */
class MoviesDataRepositoryImp(
    private val dataBase: MoviesLocalDataBase
) : MoviesDataRepository {
    private var genres = emptyList<Genre>()

    /**
     * Сохраняет в [movies] и возвращает список фильмов
     *
     * @param needRefresh принудительная загрузка из интернета
     * @return список фильмов
     */
    override suspend fun getMovies(needRefresh: Boolean): List<Movie> = withContext(Dispatchers.IO) {
        loadGenres()
        var movies = dataBase.movieDao.getAllFilms().map { toMovie(it) }
        if (movies.isEmpty() || needRefresh) {
            movies = RetrofitModule.moviesApi.loadMovies().movies.map { updateMovieGenre(it) }
            dataBase.movieDao.insertAll(movies.map { toMovieEntity(it) })
        }
        movies
    }

    /**
     * Возвращает объект фильма по id
     *
     * @param mId id фильма
     * @return объект фильма
     */
    override suspend fun getMovieById(mId: Long): MovieDetails = withContext(Dispatchers.IO) {
        var movieDetails = dataBase.movieDao.getFilmById(mId)
        if (movieDetails == null) {
            movieDetails = toMovieDetailsEntity(RetrofitModule.moviesApi.loadMovie(mId))
            dataBase.movieDao.insertMovie(movieDetails)
        }
        toMovieDetails(movieDetails)
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
     * Загружает список жанров если он пустой
     *
     * @return список жанров
     */
    override suspend fun loadGenres() =
        withContext(Dispatchers.IO) {
            if (genres.isEmpty()) genres = RetrofitModule.moviesApi.loadGenres().genres
        }

    /**  */
    private fun toMovieEntity(movie: Movie) = MovieEntity(
        adult = movie.adult,
        backdropPath = movie.backdropPath,
        genreIDS = movie.genreIDS,
        filmId = movie.id,
        overview = movie.overview,
        posterPath = movie.posterPath,
        title = movie.title,
        voteAverage = movie.voteAverage,
        voteCount = movie.voteCount,
        genresString = movie.genresString
    )

    /**
     * Устанавливает фильму строку с жанрами
     *
     * @param movie объект фильма
     */
    private fun updateMovieGenre(movie: Movie): Movie {
        movie.genresString =
            genres.filter { it.id in movie.genreIDS }.joinToString(transform = { it.name })
        return movie
    }

    /**  */
    private fun toMovie(movieEntity: MovieEntity) = Movie(
        adult = movieEntity.adult,
        backdropPath = movieEntity.backdropPath,
        genreIDS = movieEntity.genreIDS,
        id = movieEntity.filmId,
        overview = movieEntity.overview,
        posterPath = movieEntity.posterPath,
        title = movieEntity.title,
        voteAverage = movieEntity.voteAverage,
        voteCount = movieEntity.voteCount,
        genresString = movieEntity.genresString
    )

    /**  */
    private fun toMovieDetailsEntity(movieDetails: MovieDetails) = MovieDetailsEntity(
        adult = movieDetails.adult,
        backdropPath = movieDetails.backdropPath,
        genres = movieDetails.genres,
        filmId = movieDetails.id,
        overview = movieDetails.overview,
        posterPath = movieDetails.posterPath,
        tagline = movieDetails.tagline,
        title = movieDetails.title,
        voteAverage = movieDetails.voteAverage,
        voteCount = movieDetails.voteCount,

        )

    /**  */
    private fun toMovieDetails(movieDetailsEntity: MovieDetailsEntity) = MovieDetails(
        adult = movieDetailsEntity.adult,
        backdropPath = movieDetailsEntity.backdropPath,
        genres = movieDetailsEntity.genres,
        id = movieDetailsEntity.filmId,
        overview = movieDetailsEntity.overview,
        posterPath = movieDetailsEntity.posterPath,
        tagline = movieDetailsEntity.tagline,
        title = movieDetailsEntity.title,
        voteAverage = movieDetailsEntity.voteAverage,
        voteCount = movieDetailsEntity.voteCount
    )
}