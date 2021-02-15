package ru.s1mple.myapp.database

import androidx.room.*


@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    suspend fun getAllFilms(): List<MovieEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieDetails: MovieDetailsEntity): Int

    @Query("SELECT * FROM movieDetails WHERE id == :mId")
    suspend fun getFilmById(mId: Long): MovieDetailsEntity?

    @Query("SELECT * FROM movies ORDER BY vote_average DESC LIMIT 1")
    suspend fun getTopRatedMovie(): MovieEntity
}