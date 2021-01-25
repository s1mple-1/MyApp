package ru.s1mple.myapp.database

import android.content.Context
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.s1mple.myapp.data.Genre

@Database(entities = [MovieEntity::class, MovieDetailsEntity::class], version = 1)
@TypeConverters(GenresConverter::class)
abstract class MoviesLocalDataBase : RoomDatabase() {

    abstract val movieDao: MovieDao

    companion object {
        fun createDataBase(application: Context): MoviesLocalDataBase = Room.databaseBuilder(
            application,
            MoviesLocalDataBase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}

const val DATABASE_NAME = "Movies.db"

class GenresConverter {
    @TypeConverter
    fun convertLongToString(genres: List<Long>) : String = genres.joinToString()

    @TypeConverter
    fun convertStringToLong(genres: String) : List<Long> = genres.split(", ").map { it.toLong() }

    @TypeConverter
    fun convertGenresToString(genres: List<Genre>) : String = Gson().toJson(genres)

    @TypeConverter
    fun convertStringToGenre(genres: String) : List<Genre> {
        val token: TypeToken<List<Genre>> = object : TypeToken<List<Genre>>() {}
        return Gson().fromJson(genres, token.type)
    }
}