package com.anmolsahi.data.model.remote.preview

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Preview(
    @SerialName("enabled")
    val enabled: Boolean? = null,
    @SerialName("images")
    val images: List<Image?>? = null,
) {
    @Serializable
    data class Image(
        @SerialName("id")
        val id: String? = null,
        @SerialName("resolutions")
        val resolutions: List<Resolution?>? = null,
        @SerialName("source")
        val source: Source? = null,
        @SerialName("variants")
        val variants: Variants? = null,
    ) {
        @Serializable
        data class Resolution(
            @SerialName("height")
            val height: Int? = null,
            @SerialName("url")
            val url: String? = null,
            @SerialName("width")
            val width: Int? = null,
        )

        @Serializable
        data class Source(
            @SerialName("height")
            val height: Int? = null,
            @SerialName("url")
            val url: String? = null,
            @SerialName("width")
            val width: Int? = null,
        )

        @Serializable
        class Variants
    }
}
