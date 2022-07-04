package ru.alexeybuchnev.androidacademyprojeckt.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieListResponse(
    @SerialName("page")
    val page: Int? = null,
    @SerialName("total_pages")
    val totalPages: Int? = null,
    @SerialName("results")
    val results: List<MovieListItemResponse>? = null,
    @SerialName("total_results")
    val totalResults: Int? = null
)