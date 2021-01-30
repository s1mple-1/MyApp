package ru.s1mple.myapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetails (
    @SerialName("adult")
    val adult: Boolean,

    @SerialName("backdrop_path")
    val backdropPath: String,

//    @SerialName("belongs_to_collection")
//    val belongsToCollection: BelongsToCollection?,

//    val budget: Long,
    @SerialName("genres")
    val genres: List<Genre>,
//    val homepage: String,
    @SerialName("id")
    val id: Long,

//    @SerialName("imdb_id")
//    val imdbID: String,

//    @SerialName("original_language")
//    val originalLanguage: String,

//    @SerialName("original_title")
//    val originalTitle: String,
    @SerialName("overview")
    val overview: String,
//    val popularity: Double,

    @SerialName("poster_path")
    val posterPath: String,

//    @SerialName("production_companies")
//    val productionCompanies: List<ProductionCompany>,
//
//    @SerialName("production_countries")
//    val productionCountries: List<ProductionCountry>,

//    @SerialName("release_date")
//    val releaseDate: String,

//    val revenue: Long,
//    val runtime: Long,

//    @SerialName("spoken_languages")
//    val spokenLanguages: List<SpokenLanguage>,

//    val status: String,
    @SerialName("tagline")
    val tagline: String,

    @SerialName("title")
    val title: String,
//    val video: Boolean,

    @SerialName("vote_average")
    val voteAverage: Double,

    @SerialName("vote_count")
    val voteCount: Long
) {
    fun getMinimumAge(): String = if (adult) "16" else "13"
}

//@Serializable
//data class BelongsToCollection (
//    val id: Long,
//    val name: String,
//
//    @SerialName("poster_path")
//    val posterPath: String,
//
//    @SerialName("backdrop_path")
//    val backdropPath: String
//)



//@Serializable
//data class ProductionCompany (
//    val id: Long,
//
//    @SerialName("logo_path")
//    val logoPath: String? = null,
//
//    val name: String,
//
//    @SerialName("origin_country")
//    val originCountry: String
//)

//@Serializable
//data class ProductionCountry (
//    @SerialName("iso_3166_1")
//    val iso3166_1: String,
//
//    val name: String
//)

//@Serializable
//data class SpokenLanguage (
//    @SerialName("english_name")
//    val englishName: String,
//
//    @SerialName("iso_639_1")
//    val iso639_1: String,
//
//    val name: String
//)