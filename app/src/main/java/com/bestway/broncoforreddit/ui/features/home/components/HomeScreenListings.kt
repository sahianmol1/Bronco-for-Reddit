package com.bestway.broncoforreddit.ui.features.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bestway.broncoforreddit.data.models.ListingsChildren
import com.bestway.broncoforreddit.ui.features.common.widgets.BRLinearProgressIndicator
import com.bestway.broncoforreddit.ui.features.home.recycleradapter.PostAdapter

@Composable
fun HomeScreenListings(
    list: List<ListingsChildren>
) {
    val context = LocalContext.current
    AnimatedVisibility(
        visible = list.isNotEmpty(),
        enter = slideInVertically(
            initialOffsetY = { screenHeight ->
                screenHeight / 2
            }
        )
    ) {

        AndroidView(
            factory = {

                return@AndroidView RecyclerView(context)
            },
            update = { recyclerView ->
                val adapter = PostAdapter()
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = adapter
                adapter.submitList(list)
            }
        )
    }

    if (list.isEmpty()) {
        BRLinearProgressIndicator()
    }
}