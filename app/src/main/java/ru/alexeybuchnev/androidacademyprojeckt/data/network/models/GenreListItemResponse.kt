package ru.alexeybuchnev.androidacademyprojeckt.data.network.models

import kotlinx.serialization.Serializable

@Serializable
data class GenreListItemResponse(val id: Int, val name: String)