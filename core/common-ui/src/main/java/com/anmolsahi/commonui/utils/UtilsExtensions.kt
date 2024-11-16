package com.anmolsahi.commonui.utils

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.withContext
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

@Composable
fun ScrollState.ScrollHelper(resetScroll: Boolean, postScroll: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = resetScroll) {
        if (resetScroll) {
            withContext(coroutineScope.coroutineContext) { animateScrollTo(0) }
            postScroll()
        }
    }
}
