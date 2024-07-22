package com.anmolsahi.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anmolsahi.commonui.models.RedditPostUiModel
import com.anmolsahi.domain.repositories.SavedPostRepository
import com.anmolsahi.domain.usecase.DeleteSavedPostUseCase
import com.anmolsahi.presentation.utils.asUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.annotation.concurrent.Immutable
import javax.inject.Inject
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

@HiltViewModel
class SavedPostsViewModel
@Inject
constructor(
    private val repository: SavedPostRepository,
    private val deleteSavedPostUseCase: DeleteSavedPostUseCase,
) : ViewModel() {
    private val _savedPostsUiState: MutableStateFlow<PostsUiState> =
        MutableStateFlow(PostsUiState())
    val savedPostsUiState: StateFlow<PostsUiState> = _savedPostsUiState.asStateFlow()

    fun getAllSavedPosts() {
        repository
            .getAllSavedPosts()
            .onStart {
                _savedPostsUiState.update { it.copy(isLoading = true) }
            }.onEach { redditPosts ->
                _savedPostsUiState.update {
                    it.copy(
                        data = redditPosts.asUiModel(),
                        isLoading = false,
                        errorMessage = null,
                    )
                }
            }.catch { throwable ->
                _savedPostsUiState.update { it.copy(errorMessage = throwable.localizedMessage) }
            }.launchIn(viewModelScope)
    }

    fun deleteSavedPost(id: String) {
        val coroutineExceptionHandler =
            CoroutineExceptionHandler { _, throwable ->
                throwable.printStackTrace()
            }

        viewModelScope.launch(coroutineExceptionHandler) {
            deleteSavedPostUseCase(postId = id)
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
