package com.anmolsahi.data.model.remote.mediametada

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaMetadata(
    @SerialName("e")
    val e: String? = null,
    @SerialName("id")
    val id: String? = null,
    @SerialName("m")
    val m: String? = null,
    @SerialName("p")
    val p: List<P?>? = null,
    @SerialName("s")
    val s: S? = null,
    @SerialName("status")
    val status: String? = null,
) {
    @Serializable
    data class P(
        @SerialName("u")
        val u: String? = null,
        @SerialName("x")
        val x: Int? = null,
        @SerialName("y")
        val y: Int? = null,
    )

    @Serializable
    data class S(
        @SerialName("u")
        val u: String? = null,
        @SerialName("x")
        val x: Int? = null,
        @SerialName("y")
        val y: Int? = null,
    )
}
