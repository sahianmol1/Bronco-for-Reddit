package com.bestway.broncoforreddit.ui.features.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bestway.broncoforreddit.data.models.ListingsChildren
import com.bestway.broncoforreddit.ui.features.common.widgets.BRLinearProgressIndicator

@Composable
fun HomeScreenListings(
    list: List<ListingsChildren>
) {
    val scrollState = rememberScrollState()
    AnimatedVisibility(
        visible = list.isNotEmpty(),
        enter = slideInVertically(
            initialOffsetY = { screenHeight ->
                screenHeight / 2
            }
        )
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
        ) {
            list.forEachIndexed { index, listingsChildren ->
                PostWidget(
                    modifier = when (index) {
                        list.size - 1 -> Modifier.navigationBarsPadding()
                        else -> Modifier
                    },
                    subName = listingsChildren.childrenData.subName.orEmpty(),
                    title = listingsChildren.childrenData.title,
                    description = listingsChildren.childrenData.description,
                    imageUrl = listingsChildren.childrenData.imageUrl,
                    postUrl = listingsChildren.childrenData.postUrl,
                    upVotes = listingsChildren.childrenData.upVotes ?: 0,
                    comments = listingsChildren.childrenData.comments ?: 0,
                    videoUrl = listingsChildren.childrenData.secureMedia?.redditVideo?.videoUrl
                )
            }
        }
    }

    if (list.isEmpty()) {
        BRLinearProgressIndicator()
    }
}