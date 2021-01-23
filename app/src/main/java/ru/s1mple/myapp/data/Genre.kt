package ru.s1mple.myapp.data

import kotlinx.serialization.Serializable

@Serializable
data class Genres (
    val genres: List<Genre>
)

@Serializable
data class Genre (
    val id: Long,
    val name: String
)

