package com.anmolsahi.commonui.utils

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import com.anmolsahi.commonui.models.RedditPostUiModel
import kotlin.math.abs

fun determineCurrentlyPlayingItem(
    listState: LazyListState,
    items: List<RedditPostUiModel>?,
): RedditPostUiModel? {
    return items?.let {
        try {
            val layoutInfo = listState.layoutInfo
            val visiblePosts = layoutInfo.visibleItemsInfo.map { items[it.index] }
            val postsWithVideo = visiblePosts.filter { it.videoUrl != null }
            return if (postsWithVideo.size == 1) {
                postsWithVideo.first()
            } else {
                val midPoint = (layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset) / 2
                val itemsFromCenter =
                    layoutInfo.visibleItemsInfo.sortedBy {
                        abs((it.offset + it.size / 2) - midPoint)
                    }
                itemsFromCenter.map { items[it.index] }.firstOrNull { it.videoUrl != null }
            }
        } catch (e: IndexOutOfBoundsException) {
            Log.e("determineCurrentlyPlayingItem", e.message.toString())
            null
        }
    }
}
