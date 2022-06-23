package ru.alexeybuchnev.androidacademyprojeckt.data

import com.android.academy.fundamentals.homework.data.JsonGenre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonGenresResponse(
    @SerialName("genres")
    val genres: List<JsonGenre>
)