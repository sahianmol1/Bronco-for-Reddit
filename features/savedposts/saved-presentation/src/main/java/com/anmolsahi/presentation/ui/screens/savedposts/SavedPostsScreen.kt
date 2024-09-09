package com.anmolsahi.presentation.ui.screens.savedposts

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anmolsahi.commonui.components.PostComponent
import com.anmolsahi.commonui.models.RedditPostUiModel
import com.anmolsahi.commonui.utils.DeleteSavedPostAlertDialog
import com.anmolsahi.commonui.utils.ErrorDialog
import com.anmolsahi.commonui.utils.animateScrollToTop
import com.anmolsahi.commonui.utils.shareRedditPost
import com.anmolsahi.designsystem.uicomponents.BRLinearProgressIndicator
import com.anmolsahi.designsystem.uicomponents.BRScrollToTopButton
import com.anmolsahi.designsystem.utils.slideInFromBottom
import com.anmolsahi.designsystem.utils.slideOutToBottom
import com.anmolsahi.presentation.ui.components.EmptySavedPostsComponent
import kotlinx.coroutines.launch

@Composable
internal fun SavedPostsView(
    modifier: Modifier = Modifier,
    viewModel: SavedPostsViewModel = hiltViewModel(),
    onVideoFullScreenIconClick: (videoUrl: String?) -> Unit,
    onImageFullScreenIconClick: (List<String>) -> Unit,
    onPostClick: (postId: String, postUrl: String) -> Unit = { _, _ -> },
    onSaveIconClick: (String) -> Unit = {},
) {
    val uiState by remember { viewModel.uiState }.collectAsStateWithLifecycle()

    SavedPostsView(
        uiState = uiState,
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
                savedPostsList = uiState.savedPosts,
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
    onVideoFullScreenIconClick: (videoUrl: String?) -> Unit,
    onImageFullScreenIconClick: (List<String>) -> Unit,
    onPostClick: (postId: String, postUrl: String) -> Unit,
    onSaveIconClick: (String) -> Unit,
    onDeleteSavedPost: (postId: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    var selectedPostId by rememberSaveable { mutableStateOf("") }
    var showDeletePostAlertDialog by rememberSaveable { mutableStateOf(false) }
    val scrollToTopButtonModifier =
        when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> Modifier.navigationBarsPadding()
            else -> Modifier
        }

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
                        onVideoFullScreenIconClick = onVideoFullScreenIconClick,
                        onImageFullScreenIconClick = onImageFullScreenIconClick,
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomEnd,
            ) {
                AnimatedVisibility(
                    visible = lazyListState.canScrollBackward && savedPostsList.isNotEmpty(),
                    enter = slideInFromBottom(),
                    exit = slideOutToBottom(),
                ) {
                    BRScrollToTopButton(
                        modifier = scrollToTopButtonModifier,
                    ) {
                        coroutineScope.launch { lazyListState.animateScrollToTop() }
                    }
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
