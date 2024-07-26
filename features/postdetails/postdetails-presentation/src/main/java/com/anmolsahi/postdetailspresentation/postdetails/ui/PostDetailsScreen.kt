package com.anmolsahi.postdetailspresentation.postdetails.ui

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anmolsahi.commonui.components.CommentsView
import com.anmolsahi.commonui.components.OriginalPosterName
import com.anmolsahi.commonui.components.PostActions
import com.anmolsahi.commonui.components.PostImage
import com.anmolsahi.commonui.components.PostVideo
import com.anmolsahi.commonui.components.SubRedditName
import com.anmolsahi.commonui.utils.DeleteSavedPostAlertDialog
import com.anmolsahi.commonui.utils.ErrorDialog
import com.anmolsahi.designsystem.uicomponents.BRLinearProgressIndicator
import com.anmolsahi.designsystem.utils.showToast
import com.anmolsahi.commonui.R as commonUiR

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
    val scrollState = rememberScrollState()
    val uiState by viewModel.postDetailsUiState.collectAsStateWithLifecycle(
        lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current,
    )
    var showDeletePostAlertDialog by rememberSaveable { mutableStateOf(false) }
    var showErrorDialog by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getPostDetails(
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
        Column(
            modifier = modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            SubRedditName(
                subName = uiState.data?.subName.orEmpty(),
            )

            OriginalPosterName(
                opName = uiState.data?.author.orEmpty(),
            )

            if (!uiState.data?.title.isNullOrBlank()) {
                PostDetailsTitle(title = uiState.data?.title.orEmpty())
            }

            if (!uiState.data?.description.isNullOrBlank()) {
                PostDetailsDescription(
                    description = uiState.data?.description.orEmpty(),
                )
            }

            uiState.data?.imageUrl?.let {
                if (it.endsWith("png") || it.endsWith("jpg")) {
                    PostImage(
                        modifier = Modifier.zIndex(1f),
                        imageUrl = uiState.data?.imageUrl.orEmpty(),
                    )
                }
                if (it.contains(".gif")) {
                    uiState.data?.gifUrl?.let {
                        PostVideo(
                            modifier = Modifier.zIndex(1f),
                            videoUrl = uiState.data?.gifUrl.orEmpty(),
                        )
                    }
                }
            }

            uiState.data?.videoUrl?.let { videoUrl ->
                PostVideo(
                    modifier = Modifier.zIndex(1f),
                    videoUrl = videoUrl,
                )
            }

            PostActions(
                modifier = Modifier.padding(top = 8.dp),
                upVotes = uiState.data?.upVotes ?: 0,
                comments = uiState.data?.comments ?: 0,
                isSaved = uiState.data?.isSaved ?: false,
                shouldShowDeleteIcon = isFromSavedPosts,
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

            HorizontalDivider()

            for (i in 0..20) {
                CommentsView()
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
