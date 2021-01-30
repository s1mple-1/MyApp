package ru.s1mple.myapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Genres (
    @SerialName("genres")
    val genres: List<Genre>
)

@Serializable
data class Genre (
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String
)

