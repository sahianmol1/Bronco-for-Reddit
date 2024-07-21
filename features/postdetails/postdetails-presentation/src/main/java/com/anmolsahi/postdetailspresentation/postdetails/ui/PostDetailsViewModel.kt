package com.anmolsahi.postdetailspresentation.postdetails.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anmolsahi.common_ui.mappers.asUiModel
import com.anmolsahi.common_ui.models.RedditPostUiModel
import com.anmolsahi.postdetailsdomain.delegate.PostDetailsDelegate
import com.anmolsahi.postdetailsdomain.usecase.DeleteSavedPostUseCase
import com.anmolsahi.postdetailsdomain.usecase.GetPostDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel
    @Inject
    constructor(
        private val delegate: PostDetailsDelegate,
        private val deleteSavedPostUseCase: DeleteSavedPostUseCase,
        private val getPostDetailsUseCase: GetPostDetailsUseCase,
    ) : ViewModel() {
        private val _postDetailsUiState: MutableStateFlow<PostDetailsUiState> =
            MutableStateFlow(
                PostDetailsUiState(),
            )
        val postDetailsUiState: StateFlow<PostDetailsUiState> = _postDetailsUiState.asStateFlow()

        private val coroutineExceptionHandler =
            CoroutineExceptionHandler { _, throwable ->
                _postDetailsUiState.update {
                    it.copy(
                        isLoading = false,
                        error = throwable.localizedMessage,
                    )
                }
            }

        fun getPostDetails(
            postId: String,
            postUrl: String,
            isSavedPostsFlow: Boolean,
        ) {
            viewModelScope.launch(coroutineExceptionHandler) {
                _postDetailsUiState.update { it.copy(isLoading = true) }

                val post =
                    getPostDetailsUseCase(
                        postId = postId,
                        isSavedPostsFlow = isSavedPostsFlow,
                        postUrl = postUrl,
                    ).asUiModel()
                _postDetailsUiState.update {
                    it.copy(isLoading = false, data = post)
                }
            }
        }

        fun onSaveIconClick(
            postId: String,
            postUrl: String,
            isSavedPostsFlow: Boolean,
            onPostSaved: (Boolean) -> Unit,
        ) {
            viewModelScope.launch {
                val isPostSaved = delegate.togglePostSavedStatus(postId)
                delegate.updateSavedPosts(isPostSaved, postId)
                getPostDetails(postId = postId, isSavedPostsFlow = isSavedPostsFlow, postUrl = postUrl)
                onPostSaved(isPostSaved)
            }
        }

        fun deleteSavedPost(postId: String) {
            val coroutineExceptionHandler =
                CoroutineExceptionHandler { _, throwable ->
                    throwable.printStackTrace()
                }

            viewModelScope.launch(coroutineExceptionHandler) {
                deleteSavedPostUseCase(postId = postId)
            }
        }
    }

data class PostDetailsUiState(
    val data: RedditPostUiModel? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
