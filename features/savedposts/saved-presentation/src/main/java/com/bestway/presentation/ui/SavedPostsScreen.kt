package com.bestway.presentation.ui

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anmolsahi.common_ui.components.PostComponent
import com.anmolsahi.common_ui.models.RedditPostUiModel
import com.anmolsahi.common_ui.utils.DeleteSavedPostAlertDialog
import com.anmolsahi.common_ui.utils.ErrorDialog
import com.anmolsahi.common_ui.utils.scrollToTop
import com.bestway.design_system.ui_components.BRLinearProgressIndicator

@Composable
fun SavedPostsScreen(
    modifier: Modifier = Modifier,
    viewModel: SavedPostsViewModel = hiltViewModel(),
    onClick: (String) -> Unit = {},
    onSaveIconClick: (String) -> Unit = {},
) {
    val uiState by
    viewModel.savedPostsUiState.collectAsStateWithLifecycle(
        lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current
    )
    val lazyListState = rememberLazyListState()
    var list by remember { mutableStateOf(emptyList<RedditPostUiModel>()) }
    var showDeletePostAlertDialog by rememberSaveable { mutableStateOf(false) }
    var selectedPostId by rememberSaveable { mutableStateOf("") }
    var showErrorDialog by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(uiState.data) {
        viewModel.getAllSavedPosts()
        list = uiState.data.orEmpty()
        lazyListState.scrollToTop()
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
            }
        )
    }

    if (list.isNotEmpty()) {
        LazyColumn(
            modifier = modifier,
            state = lazyListState,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            itemsIndexed(
                items = list,
                key = { _, item -> item.id },
                contentType = { _, _ -> "reddit_post" }
            ) { index, item ->
                PostComponent(
                    modifier =
                    when (index) {
                        0 -> Modifier.statusBarsPadding()
                        list.size - 1 -> Modifier.navigationBarsPadding()
                        else -> Modifier
                    },
                    redditPostUiModel = item,
                    onClick = onClick,
                    onSaveIconClick = onSaveIconClick,
                    shouldShowDeleteIcon = true,
                    onDeleteIconClick = { redditPostId ->
                        selectedPostId = redditPostId
                        showDeletePostAlertDialog = true
                    }
                )
            }
        }
    }

    // Show error screen
    if (showErrorDialog) {
        ErrorDialog(
            errorMessage = uiState.errorMessage.orEmpty(),
            onConfirmButtonClick = {
                showErrorDialog = false
            }
        )
    }

    if (uiState.isLoading) {
        BRLinearProgressIndicator()
    }
}
