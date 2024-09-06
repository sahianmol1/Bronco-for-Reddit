package com.anmolsahi.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anmolsahi.commonui.models.RedditPostUiModel
import com.anmolsahi.domain.repositories.SavedPostRepository
import com.anmolsahi.domain.usecase.DeleteSavedPostUseCase
import com.anmolsahi.presentation.utils.asUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SavedPostsViewModel @Inject constructor(
    private val repository: SavedPostRepository,
    private val deleteSavedPostUseCase: DeleteSavedPostUseCase,
) : ViewModel() {

    private val _isLoading: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val isLoading: SharedFlow<Boolean>
        get() = _isLoading.asSharedFlow()

    val savedPosts: StateFlow<Result<List<RedditPostUiModel>>> = repository.getAllSavedPosts()
        .onStart { _isLoading.emit(true) }
        .map { result ->
            _isLoading.emit(false)
            result.map { data -> data.asUiModel().reversed() }
        }
        .catch { _isLoading.emit(false) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Result.success(emptyList()),
        )

    fun deleteSavedPost(id: String) {
        val coroutineExceptionHandler =
            CoroutineExceptionHandler { _, throwable ->
                throwable.printStackTrace()
            }

        viewModelScope.launch(coroutineExceptionHandler) {
            deleteSavedPostUseCase(postId = id)
        }
    }
}
