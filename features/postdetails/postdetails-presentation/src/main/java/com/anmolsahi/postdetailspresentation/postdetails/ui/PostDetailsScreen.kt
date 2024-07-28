package com.anmolsahi.postdetailspresentation.postdetails.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anmolsahi.commonui.utils.DeleteSavedPostAlertDialog
import com.anmolsahi.commonui.utils.ErrorDialog
import com.anmolsahi.commonui.utils.animateScrollToTop
import com.anmolsahi.designsystem.uicomponents.BRLinearProgressIndicator
import com.anmolsahi.designsystem.uicomponents.BRScrollToTop
import com.anmolsahi.designsystem.utils.showToast
import com.anmolsahi.designsystem.utils.slideInFromBottomTransition
import com.anmolsahi.designsystem.utils.slideOutToBottomTransition
import com.anmolsahi.postdetailspresentation.R
import com.anmolsahi.postdetailspresentation.postdetails.components.CommentsComponent
import com.anmolsahi.postdetailspresentation.postdetails.components.PostDetailsComponent
import com.anmolsahi.postdetailspresentation.postdetails.ui.PostDetailsScreenValues.TYPE_COMMENTS_HEADING
import com.anmolsahi.postdetailspresentation.postdetails.ui.PostDetailsScreenValues.TYPE_COMMENTS_SECTION
import com.anmolsahi.postdetailspresentation.postdetails.ui.PostDetailsScreenValues.TYPE_DIVIDER
import com.anmolsahi.postdetailspresentation.postdetails.ui.PostDetailsScreenValues.TYPE_LOADING
import com.anmolsahi.postdetailspresentation.postdetails.ui.PostDetailsScreenValues.TYPE_POST_DETAILS
import com.anmolsahi.postdetailspresentation.postdetails.utils.shouldShowCommentsComponent
import kotlinx.coroutines.launch
import com.anmolsahi.commonui.R as commonUiR

private object PostDetailsScreenValues {
    const val TYPE_POST_DETAILS = "post_details"
    const val TYPE_DIVIDER = "divider"
    const val TYPE_LOADING = "loading"
    const val TYPE_COMMENTS_HEADING = "comments_heading"
    const val TYPE_COMMENTS_SECTION = "comments_section"
}

@Composable
fun PostDetailsScreen(
    postId: String,
    postUrl: String,
    isFromSavedPosts: Boolean,
    modifier: Modifier = Modifier,
    viewModel: PostDetailsViewModel = hiltViewModel(),
    popBackStack: () -> Unit = {},
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val uiState by viewModel.postDetailsUiState.collectAsStateWithLifecycle(
        lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current,
    )
    var showDeletePostAlertDialog by rememberSaveable { mutableStateOf(false) }
    var showErrorDialog by rememberSaveable { mutableStateOf(false) }
    val comments by remember(uiState) { mutableStateOf(uiState.postComments.orEmpty()) }

    LaunchedEffect(Unit) {
        viewModel.getPostDetails(
            postId = postId,
            postUrl = postUrl,
        )
    }

    LaunchedEffect(Unit) {
        viewModel.getPostComments(
            postId = postId,
            postUrl = postUrl,
        )
    }

    LaunchedEffect(uiState.error) {
        if (!uiState.error.isNullOrEmpty()) {
            showErrorDialog = true
        }
    }

    if (showDeletePostAlertDialog) {
        DeleteSavedPostAlertDialog(
            onDismissButtonClick = {
                showDeletePostAlertDialog = false
            },
            onConfirmButtonClick = {
                viewModel.deleteSavedPost(postId)
                showDeletePostAlertDialog = false
                popBackStack()
            },
        )
    }

    if (uiState.isLoading) {
        BRLinearProgressIndicator()
    }

    if (!uiState.isLoading) {
        Box(
            contentAlignment = Alignment.BottomEnd,
        ) {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                state = lazyListState,
                contentPadding = WindowInsets.systemBars.asPaddingValues(),
            ) {
                item(
                    contentType = { TYPE_POST_DETAILS },
                ) {
                    PostDetailsComponent(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        uiState = uiState,
                        isFromSavedPosts = isFromSavedPosts,
                        onSaveIconClick = {
                            viewModel.onSaveIconClick(
                                post = uiState.data,
                            ) { isSaved ->
                                if (isSaved) {
                                    context.showToast(
                                        context.getString(commonUiR.string.post_saved_success),
                                    )
                                }
                            }
                        },
                        onDeleteIconClick = {
                            showDeletePostAlertDialog = true
                        },
                    )
                }

                item(
                    contentType = { TYPE_DIVIDER },
                ) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    )
                }

                item(
                    contentType = { TYPE_LOADING },
                ) {
                    if (uiState.isCommentsLoading) {
                        BRLinearProgressIndicator(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 48.dp),
                        )
                    }
                }

                item(
                    contentType = { TYPE_COMMENTS_HEADING },
                ) {
                    if (comments.isNotEmpty()) {
                        Text(
                            text = stringResource(R.string.comments),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp),
                        )
                    }
                }

                if (comments.isNotEmpty()) {
                    items(
                        count = comments.size,
                        key = { index -> comments[index].id },
                        contentType = { _ -> TYPE_COMMENTS_SECTION },
                    ) {
                        if (shouldShowCommentsComponent(comments[it])) {
                            Column {
                                CommentsComponent(
                                    modifier = Modifier.fillMaxWidth(),
                                    commentDetails = comments[it],
                                    originalPosterName = uiState.data?.author.orEmpty(),
                                )
                                HorizontalDivider(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                )
                            }
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = lazyListState.canScrollBackward,
                enter = slideInFromBottomTransition(),
                exit = slideOutToBottomTransition(),
            ) {
                BRScrollToTop {
                    coroutineScope.launch { lazyListState.animateScrollToTop() }
                }
            }
        }

        if (showErrorDialog) {
            ErrorDialog(
                errorMessage = uiState.error.orEmpty(),
                onConfirmButtonClick = { showErrorDialog = false },
            )
        }
    }
}
