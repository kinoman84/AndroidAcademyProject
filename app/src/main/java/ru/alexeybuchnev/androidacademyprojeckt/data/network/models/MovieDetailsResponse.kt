package ru.alexeybuchnev.androidacademyprojeckt.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MovieDetailsResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("poster_path")
    val posterPicture: String,
    @SerialName("backdrop_path")
    val backdropPicture: String,
    @SerialName("runtime")
    val runtime: Int? = null,
    @SerialName("genres")
    val genreIds: List<GenreListItemResponse>,
    val actors: List<Int>? = null,
    @SerialName("vote_average")
    val ratings: Float,
    @SerialName("vote_count")
    val votesCount: Int,
    @SerialName("overview")
    val overview: String,
    @SerialName("adult")
    val adult: Boolean
)