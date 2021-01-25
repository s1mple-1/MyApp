package ru.s1mple.myapp.database

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = TABLE_NAME, indices = [Index(COLUMN_NAME_ID)])
class MovieEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_NAME_ID)
    val idColumn: Long = 0,

    @ColumnInfo(name = "adult")
    val adult: Boolean,

    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String,

    @ColumnInfo(name = "genre_ids")
    val genreIDS: List<Long>,

    @ColumnInfo(name = "id")
    val filmId: Long,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "poster_path")
    val posterPath: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,

    @ColumnInfo(name = "vote_count")
    val voteCount: Long,

    @ColumnInfo(name = "genres_string")
    val genresString : String
)

const val COLUMN_NAME_ID = BaseColumns._ID
const val TABLE_NAME = "movies"


