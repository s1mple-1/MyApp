package ru.s1mple.myapp

/**
 * TODO DOC
 */
data class Film(
    val filmId: Int,
    val filmImageMain: Int,
    val filmImageDetails: Int,
    val ageRating: String,
    val filmTitleMain: String,
    val filmDurationMain: String,
    val filmReviewsCount: String,
    val tagLine: String,
    val hasLike: Boolean,
    val rating: Int,
    val filmTitleDetails: String,
    val filmDescription: String
)