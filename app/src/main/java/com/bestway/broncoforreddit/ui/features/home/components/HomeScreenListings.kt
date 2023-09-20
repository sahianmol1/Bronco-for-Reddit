package com.bestway.broncoforreddit.ui.features.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bestway.broncoforreddit.data.repositories.models.ListingsChildren
import com.bestway.broncoforreddit.ui.features.common.widgets.BRLinearProgressIndicator

@Composable
fun HomeScreenListings(
    list: List<ListingsChildren>
) {
    AnimatedVisibility(
        visible = list.isNotEmpty(),
        enter = slideInVertically(
            initialOffsetY = { screenHeight ->
                screenHeight / 2
            }
        )
    ) {
        LazyColumn {
            items(
                count = list.size,
                key = { index -> list[index].childrenData.id }
            ) { index ->
                PostWidget(
                    modifier = when (index) {
                        list.size - 1 -> Modifier.navigationBarsPadding()
                        else -> Modifier
                    },
                    subName = list[index].childrenData.subName.orEmpty(),
                    title = list[index].childrenData.title.orEmpty(),
                    description = list[index].childrenData.description.orEmpty(),
                    upVotes = list[index].childrenData.upVotes ?: 0,
                    comments = list[index].childrenData.comments ?: 0,
                )
            }
        }
    }

    if (list.isEmpty()) {
        BRLinearProgressIndicator()
    }
}