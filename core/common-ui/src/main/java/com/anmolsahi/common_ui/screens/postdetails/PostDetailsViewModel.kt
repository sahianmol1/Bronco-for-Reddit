package com.anmolsahi.common_ui.screens.postdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anmolsahi.common_ui.delegate.CommonUiDelegate
import com.anmolsahi.common_ui.models.RedditPostUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val delegate: CommonUiDelegate
): ViewModel() {

    private val _postDetails: MutableStateFlow<PostDetailsUiState> = MutableStateFlow(
        PostDetailsUiState()
    )
    val postDetails: StateFlow<PostDetailsUiState> = _postDetails.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _postDetails.update {
            it.copy(
                isLoading = false,
                error = throwable.localizedMessage
            )
        }
    }

    fun getPostDetails(postId: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            _postDetails.update { it.copy(isLoading = true) }

            val post = delegate.getPostById(postId = postId)
            _postDetails.update {
                it.copy(isLoading = false, data = post)
            }
        }
    }

    fun onSaveIconClick(postId: String, onPostSaved: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isPostSaved = delegate.updatePost(postId)
            getPostDetails(postId = postId)
            onPostSaved(isPostSaved)
        }
    }
}

data class PostDetailsUiState(
    val data: RedditPostUiModel? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)