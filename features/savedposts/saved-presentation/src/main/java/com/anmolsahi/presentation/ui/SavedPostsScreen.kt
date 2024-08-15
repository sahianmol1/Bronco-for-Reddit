package com.anmolsahi.presentation.ui

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
import androidx.compose.runtime.LaunchedEffect
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
internal fun SavedPostsScreen(
    viewModel: SavedPostsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onVideoFullScreenIconClick: (videoUrl: String?) -> Unit,
    onImageFullScreenIconClick: (List<String>) -> Unit,
    onClick: (postId: String, postUrl: String) -> Unit = { _, _ -> },
    onSaveIconClick: (String) -> Unit = {},
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.savedPostsUiState.collectAsStateWithLifecycle(
        lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current,
    )
    val lazyListState = rememberLazyListState()
    var list by remember { mutableStateOf(emptyList<RedditPostUiModel>()) }
    var showDeletePostAlertDialog by rememberSaveable { mutableStateOf(false) }
    var selectedPostId by rememberSaveable { mutableStateOf("") }
    var showErrorDialog by rememberSaveable { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val scrollToTopButtonModifier =
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Modifier.navigationBarsPadding()
        } else {
            Modifier
        }

    LaunchedEffect(Unit) {
        viewModel.getAllSavedPosts()
        list = uiState.data.orEmpty()
    }

    LaunchedEffect(uiState.data) {
        val newItemAdded = uiState.data.orEmpty().size > list.size
        list = uiState.data.orEmpty()
        if (newItemAdded) {
            lazyListState.animateScrollToTop()
        }
    }

    LaunchedEffect(uiState.errorMessage) {
        if (!uiState.errorMessage.isNullOrBlank()) {
            showErrorDialog = true
        }
    }

    if (showDeletePostAlertDialog) {
        DeleteSavedPostAlertDialog(
            onDismissButtonClick = {
                showDeletePostAlertDialog = false
            },
            onConfirmButtonClick = {
                viewModel.deleteSavedPost(selectedPostId)
                showDeletePostAlertDialog = false
            },
        )
    }

    if (list.isNotEmpty()) {
        LazyColumn(
            modifier = modifier,
            state = lazyListState,
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = WindowInsets.systemBars.asPaddingValues(),
        ) {
            itemsIndexed(
                items = list,
                key = { _, item -> item.id },
                contentType = { _, _ -> "reddit_post" },
            ) { index, item ->
                PostComponent(
                    modifier = Modifier
                        .padding(
                            bottom = if (index == list.lastIndex) {
                                68.dp
                            } else {
                                0.dp
                            },
                        ),
                    redditPostUiModel = item,
                    onClick = onClick,
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
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomEnd,
    ) {
        AnimatedVisibility(
            visible = lazyListState.canScrollBackward && list.isNotEmpty(),
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

    // Show error screen
    if (showErrorDialog) {
        ErrorDialog(
            errorMessage = uiState.errorMessage.orEmpty(),
            onConfirmButtonClick = {
                showErrorDialog = false
            },
        )
    }

    if (uiState.isLoading) {
        BRLinearProgressIndicator()
    } else if (list.isEmpty()) {
        EmptySavedPostsComponent()
    }
}
