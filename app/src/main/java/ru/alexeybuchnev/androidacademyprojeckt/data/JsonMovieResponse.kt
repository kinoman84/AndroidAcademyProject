package ru.alexeybuchnev.androidacademyprojeckt.data

import com.android.academy.fundamentals.homework.data.JsonMovie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonMovieResponse(
    @SerialName("page")
    val page: Int? = null,
    @SerialName("total_pages")
    val totalPages: Int? = null,
    @SerialName("results")
    val results: List<JsonMovie>? = null,
    @SerialName("total_results")
    val totalResults: Int? = null
)