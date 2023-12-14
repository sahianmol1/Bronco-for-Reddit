package com.bestway.models.listings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListingsResponse(
    @SerialName("kind") val kind: String? = null,
    @SerialName("data") val data: ListingsData? = null
)
