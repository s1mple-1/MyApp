package ru.s1mple.myapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    suspend fun getAllFilms() : List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieDetails: MovieDetailsEntity)

    @Query("SELECT * FROM movieDetails WHERE id == :mId")
    suspend fun getFilmById(mId : Long) : MovieDetailsEntity?

}