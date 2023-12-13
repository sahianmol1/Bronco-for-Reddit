package com.bestway.broncoforreddit.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListingsData(
    @SerialName("after")
    val after: String? = null,
    @SerialName("dist")
    val dist: Int? = null,
    @SerialName("modhash")
    val modhash: String? = null,
    @SerialName("geo_filter")
    val geoFilter: String? = null,
    @SerialName("children")
    val children: List<ListingsChildren>? = null,
    @SerialName("before")
    val before: String? = null
)