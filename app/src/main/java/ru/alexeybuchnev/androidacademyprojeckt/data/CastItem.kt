package ru.alexeybuchnev.androidacademyprojeckt.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastItem(
//    val castId: Int? = null,
//    val character: String? = null,
//    val gender: Int? = null,
//    val creditId: String? = null,
//    val knownForDepartment: String? = null,
//    val originalName: String? = null,
//    val popularity: Double? = null,
    @SerialName("name")
    val name: String,
    @SerialName("profile_path")
    val profilePath: String? = null,
    @SerialName("id")
    val id: Int,
//    val adult: Boolean? = null,
//    val order: Int? = null
)
