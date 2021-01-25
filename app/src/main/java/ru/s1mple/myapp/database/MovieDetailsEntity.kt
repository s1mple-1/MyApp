package ru.s1mple.myapp.database

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.s1mple.myapp.data.Genre

@Entity(tableName = TABLE_NAME_DETAILS, indices = [Index(COLUMN_NAME_ID_DETAILS)])
class MovieDetailsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_NAME_ID_DETAILS)
    val id: Long = 0,

    @ColumnInfo(name = "adult")
    val adult: Boolean,

    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String,

    @ColumnInfo(name = "genre_ids")
    val genres: List<Genre>,

    @ColumnInfo(name = "id")
    val filmId: Long,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "poster_path")
    val posterPath: String,

    @ColumnInfo(name = "tagline")
    val tagline: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,

    @ColumnInfo(name = "vote_count")
    val voteCount: Long
)

const val COLUMN_NAME_ID_DETAILS = BaseColumns._ID
const val TABLE_NAME_DETAILS = "movieDetails"