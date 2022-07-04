package ru.alexeybuchnev.androidacademyprojeckt.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditsResponse(
	@SerialName("cast")
	val cast: List<CreditsItemResponse>? = null,
	@SerialName("id")
	val id: Int? = null,
)