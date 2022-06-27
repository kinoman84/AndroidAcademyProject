package ru.alexeybuchnev.androidacademyprojeckt.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditsResponse(
	@SerialName("cast")
	val cast: List<CastItem>? = null,
	@SerialName("id")
	val id: Int? = null,
)