package com.bestway.broncoforreddit.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListingsResponse(
    @SerialName("kind")
    val kind: String? = null,
    @SerialName("data")
    val data: ListingsData? = null
)
