package com.anmolsahi.presentation.ui.screens.savedposts

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.exoplayer.ExoPlayer
import com.anmolsahi.commonui.components.PostComponent
import com.anmolsahi.commonui.models.RedditPostUiModel
import com.anmolsahi.commonui.utils.DeleteSavedPostAlertDialog
import com.anmolsahi.commonui.utils.ErrorDialog
import com.anmolsahi.commonui.utils.ScrollHelper
import com.anmolsahi.commonui.utils.determineCurrentlyPlayingItem
import com.anmolsahi.commonui.utils.shareRedditPost
import com.anmolsahi.designsystem.uicomponents.BRLinearProgressIndicator
import com.anmolsahi.presentation.ui.components.EmptySavedPostsComponent

@Composable
internal fun SavedPostsScreen(
    resetScroll: Boolean,
    postScroll: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SavedPostsViewModel = hiltViewModel(),
    onVideoFullScreenIconClick: (videoUrl: String?) -> Unit,
    onImageFullScreenIconClick: (List<String>) -> Unit,
    onPostClick: (postId: String, postUrl: String) -> Unit = { _, _ -> },
    onSaveIconClick: (String) -> Unit = {},
) {
    val context = LocalContext.current
    val uiState by remember { viewModel.uiState }.collectAsStateWithLifecycle()
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build()
    }

    SavedPostsView(
        uiState = uiState,
        exoPlayer = exoPlayer,
        resetScroll = resetScroll,
        postScroll = postScroll,
        modifier = modifier,
        onVideoFullScreenIconClick = onVideoFullScreenIconClick,
        onImageFullScreenIconClick = onImageFullScreenIconClick,
        onPostClick = onPostClick,
        onSaveIconClick = onSaveIconClick,
        onDeleteSavedPost = { postId ->
            viewModel.deleteSavedPost(postId)
        },
    )
}

@Composable
fun SavedPostsView(
    uiState: SavedPostsUiState,
    exoPlayer: ExoPlayer,
    resetScroll: Boolean,
    postScroll: () -> Unit,
    modifier: Modifier = Modifier,
    onVideoFullScreenIconClick: (videoUrl: String?) -> Unit,
    onImageFullScreenIconClick: (List<String>) -> Unit,
    onPostClick: (postId: String, postUrl: String) -> Unit = { _, _ -> },
    onSaveIconClick: (String) -> Unit = {},
    onDeleteSavedPost: (postId: String) -> Unit = {},
) {
    var showErrorDialog by rememberSaveable { mutableStateOf(false) }

    when (uiState) {
        is SavedPostsUiState.Loading -> {
            BRLinearProgressIndicator()
        }

        is SavedPostsUiState.Success -> {
            SavedPostsListView(
                exoPlayer = exoPlayer,
                savedPostsList = uiState.savedPosts,
                resetScroll = resetScroll,
                postScroll = postScroll,
                modifier = modifier,
                onVideoFullScreenIconClick = onVideoFullScreenIconClick,
                onImageFullScreenIconClick = onImageFullScreenIconClick,
                onPostClick = onPostClick,
                onSaveIconClick = onSaveIconClick,
                onDeleteSavedPost = onDeleteSavedPost,
            )
        }

        is SavedPostsUiState.Failure -> {
            if (showErrorDialog) {
                ErrorDialog(
                    errorMessage = uiState.error,
                    onConfirmButtonClick = {
                        showErrorDialog = false
                    },
                )
            }
        }
    }
}

@Composable
fun SavedPostsListView(
    savedPostsList: List<RedditPostUiModel>,
    resetScroll: Boolean,
    exoPlayer: ExoPlayer,
    postScroll: () -> Unit,
    onVideoFullScreenIconClick: (videoUrl: String?) -> Unit,
    onImageFullScreenIconClick: (List<String>) -> Unit,
    onPostClick: (postId: String, postUrl: String) -> Unit,
    onSaveIconClick: (String) -> Unit,
    onDeleteSavedPost: (postId: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val lazyListState = rememberLazyListState()
    var selectedPostId by rememberSaveable { mutableStateOf("") }
    var showDeletePostAlertDialog by rememberSaveable { mutableStateOf(false) }
    lazyListState.ScrollHelper(resetScroll = resetScroll, postScroll)
    val currentlyPlayingItem = determineCurrentlyPlayingItem(lazyListState, savedPostsList)

    when (savedPostsList.isEmpty()) {
        true -> {
            EmptySavedPostsComponent()
        }

        false -> {
            LazyColumn(
                modifier = modifier,
                state = lazyListState,
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = WindowInsets.systemBars.asPaddingValues(),
            ) {
                itemsIndexed(
                    items = savedPostsList,
                    key = { _, item -> item.id },
                    contentType = { _, _ -> "reddit_post" },
                ) { index, item ->
                    PostComponent(
                        modifier = Modifier
                            .padding(
                                bottom = when (index) {
                                    savedPostsList.lastIndex -> 68.dp
                                    else -> 0.dp
                                },
                            ),
                        redditPostUiModel = item,
                        onClick = onPostClick,
                        onSaveIconClick = onSaveIconClick,
                        shouldShowDeleteIcon = true,
                        onDeleteIconClick = { redditPostId ->
                            selectedPostId = redditPostId
                            showDeletePostAlertDialog = true
                        },
                        onShareIconClick = { postUrl -> shareRedditPost(postUrl, context) },
                        onImageFullScreenIconClick = onImageFullScreenIconClick,
                    )
                }
            }

            if (showDeletePostAlertDialog) {
                DeleteSavedPostAlertDialog(
                    onDismissButtonClick = {
                        showDeletePostAlertDialog = false
                    },
                    onConfirmButtonClick = {
                        onDeleteSavedPost(selectedPostId)
                        showDeletePostAlertDialog = false
                    },
                )
            }
        }
    }
}
