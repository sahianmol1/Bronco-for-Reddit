package com.anmolsahi.presentation.ui.screens.savedposts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anmolsahi.commonui.models.RedditPostUiModel
import com.anmolsahi.domain.repositories.SavedPostRepository
import com.anmolsahi.domain.usecase.DeleteSavedPostUseCase
import com.anmolsahi.presentation.utils.asUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SavedPostsViewModel @Inject constructor(
    repository: SavedPostRepository,
    private val deleteSavedPostUseCase: DeleteSavedPostUseCase,
) : ViewModel() {

    val uiState: StateFlow<SavedPostsUiState> = repository.getAllSavedPosts()
        .map { data ->
            SavedPostsUiState.Success(savedPosts = data.asUiModel().reversed())
        }
        .catch { throwable ->
            SavedPostsUiState.Failure(error = throwable.localizedMessage.orEmpty())
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SavedPostsUiState.Loading,
        )

    fun deleteSavedPost(id: String) {
        val coroutineExceptionHandler =
            CoroutineExceptionHandler { _, throwable ->
                Log.e(
                    SavedPostsViewModel::class.simpleName,
                    "deleteSavedPost: ${throwable.localizedMessage}",
                )
            }

        viewModelScope.launch(coroutineExceptionHandler) {
            deleteSavedPostUseCase(postId = id)
        }
    }
}

sealed interface SavedPostsUiState {
    data object Loading : SavedPostsUiState
    data class Success(val savedPosts: List<RedditPostUiModel>) : SavedPostsUiState
    data class Failure(val error: String) : SavedPostsUiState
}
