package com.anmolsahi.postdetailspresentation.postdetails.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anmolsahi.common_ui.R
import com.anmolsahi.common_ui.components.CommentsView
import com.anmolsahi.common_ui.components.OriginalPosterName
import com.anmolsahi.common_ui.components.PostActions
import com.anmolsahi.common_ui.components.SubRedditName
import com.anmolsahi.common_ui.utils.DeleteSavedPostAlertDialog
import com.bestway.design_system.ui_components.BRLinearProgressIndicator
import com.bestway.design_system.utils.showToast
import com.bestway.design_system.utils.slideInFromBottomTransition

@Composable
fun PostDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: PostDetailsViewModel = hiltViewModel(),
    postId: String,
    isSavedPostsFlow: Boolean,
    popBackStack: () -> Unit = {},
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val postDetails =
        viewModel.postDetails.collectAsStateWithLifecycle(
            lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current,
        )
    var showDeletePostAlertDialog by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getPostDetails(
            postId = postId,
            isSavedPostsFlow = isSavedPostsFlow,
        )
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

    if (postDetails.value.isLoading) {
        BRLinearProgressIndicator()
    }

    if (!postDetails.value.isLoading) {
        Column(
            modifier =
                modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            SubRedditName(
                subName =
                    postDetails.value.data
                        ?.subName
                        .orEmpty(),
            )

            OriginalPosterName(
                opName =
                    postDetails.value.data
                        ?.author
                        .orEmpty(),
            )

            if (!postDetails.value.data
                    ?.title
                    .isNullOrBlank()
            ) {
                PostDetailsTitle(
                    title =
                        postDetails.value.data
                            ?.title
                            .orEmpty(),
                )
            }

            if (!postDetails.value.data
                    ?.description
                    .isNullOrBlank()
            ) {
                PostDetailsDescription(
                    description =
                        postDetails.value.data
                            ?.description
                            .orEmpty(),
                )
            }

            postDetails.value.data?.imageUrl?.let {
                if (it.endsWith("png") || it.endsWith("jpg")) {
                    com.anmolsahi.common_ui.components.PostImage(
                        imageUrl =
                            postDetails.value.data
                                ?.imageUrl
                                .orEmpty(),
                    )
                }
                if (it.contains(".gif")) {
                    postDetails.value.data?.gifUrl?.let {
                        com.anmolsahi.common_ui.components.PostVideo(
                            videoUrl =
                                postDetails.value.data
                                    ?.gifUrl
                                    .orEmpty(),
                        )
                    }
                }
            }

            postDetails.value.data?.videoUrl?.let { videoUrl ->
                com.anmolsahi.common_ui.components
                    .PostVideo(videoUrl = videoUrl)
            }

            PostActions(
                modifier = Modifier.padding(top = 8.dp),
                upVotes = postDetails.value.data?.upVotes ?: 0,
                comments = postDetails.value.data?.comments ?: 0,
                isSaved = postDetails.value.data?.isSaved ?: false,
                shouldShowDeleteIcon = isSavedPostsFlow,
                onSaveIconClick = {
                    viewModel.onSaveIconClick(
                        postId =
                            postDetails.value.data
                                ?.id
                                .orEmpty(),
                        isSavedPostsFlow = isSavedPostsFlow,
                    ) { isSaved ->
                        if (isSaved) {
                            context.showToast(context.getString(R.string.post_saved_success))
                        }
                    }
                },
                onDeleteIconClick = {
                    showDeletePostAlertDialog = true
                },
            )

            HorizontalDivider()

            for (i in 0..20) {
                CommentsView()
            }
        }
    }

    AnimatedVisibility(
        visible = !postDetails.value.error.isNullOrEmpty(),
        enter = slideInFromBottomTransition(),
    ) {
        var showLogs by rememberSaveable { mutableStateOf(false) }
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // TODO: Code Cleanup
            Text(
                modifier = Modifier.clickable { showLogs = !showLogs },
                text =
                    if (!showLogs) {
                        stringResource(R.string.uh_oh_something_went_wrong) + " Learn More"
                    } else {
                        stringResource(R.string.uh_oh_something_went_wrong) +
                            " Learn More /n ${postDetails.value.error}"
                    },
                textDecoration = TextDecoration.Underline,
            )
        }
    }
}

@Composable
fun PostDetailsTitle(title: String) {
    Text(modifier = Modifier.padding(vertical = 4.dp), fontWeight = FontWeight.Bold, text = title)
}

@Composable
fun PostDetailsDescription(description: String) {
    Text(
        modifier = Modifier.padding(vertical = 4.dp),
        text = description,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.90f),
    )
}
