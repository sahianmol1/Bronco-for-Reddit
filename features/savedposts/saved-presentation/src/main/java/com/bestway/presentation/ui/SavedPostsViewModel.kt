package com.bestway.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anmolsahi.common_ui.models.RedditPostUiModel
import com.anmolsahi.postdetailspresentation.postdetails.delegate.SavedPostDelegate
import com.bestway.domain.repositories.SavedPostRepository
import com.bestway.presentation.utils.asUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class SavedPostsViewModel @Inject constructor(
    private val repository: SavedPostRepository,
    private val savedPostDelegate: SavedPostDelegate,
) : ViewModel() {

    private val _savedPostState: MutableStateFlow<PostsUiState> = MutableStateFlow(PostsUiState())
    val savedPostsUiState: StateFlow<PostsUiState> = _savedPostState.asStateFlow()

    fun getAllSavedPosts() {
        repository.getAllSavedPosts()
            .onStart {
                _savedPostState.update { it.copy(isLoading = true) }
            }
            .onEach { redditPosts ->
                _savedPostState.update {
                    it.copy(
                        data = redditPosts.asUiModel(),
                        isLoading = false,
                        errorMessage = null
                    )
                }
            }
            .catch { throwable ->
                _savedPostState.update { it.copy(errorMessage = throwable.localizedMessage) }
            }
            .launchIn(viewModelScope)
    }

    fun deleteSavedPost(id: String) {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }

        viewModelScope.launch (coroutineExceptionHandler) {
            repository.deleteSavedPost(id)
            savedPostDelegate.togglePostSavedStatus(id)
            getAllSavedPosts()
        }
    }

}

@Immutable
data class PostsUiState(
    val data: List<RedditPostUiModel>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)