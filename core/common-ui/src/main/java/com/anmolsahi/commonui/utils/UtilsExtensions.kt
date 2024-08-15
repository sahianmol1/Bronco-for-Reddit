package com.anmolsahi.commonui.utils

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun Int?.orZero() = this ?: 0

fun List<String>.serialize(): String {
    val encodedImageUrlList = this.map { imageUrl ->
        encodeUrl(imageUrl)
    }

    return Json.encodeToString(encodedImageUrlList)
}

fun String.deserialize(): List<String> = Json.decodeFromString<List<String>>(this)

private fun encodeUrl(url: String): String = URLEncoder.encode(
    /* s = */
    url,
    /* enc = */
    StandardCharsets.UTF_8.toString(),
)
