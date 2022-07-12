package ru.alexeybuchnev.androidacademyprojeckt.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * https://developers.themoviedb.org/3/movies/get-popular-movies
 */

@Serializable
class MovieListItemResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("poster_path")
    val posterPicture: String,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("vote_average")
    val ratings: Float,
    @SerialName("vote_count")
    val votesCount: Int,
    @SerialName("overview")
    val overview: String,
    @SerialName("adult")
    val adult: Boolean
)