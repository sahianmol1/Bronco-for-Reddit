package com.bestway.presentation.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anmolsahi.common_ui.models.RedditPostUiModel
import com.anmolsahi.common_ui.models.asUiModel
import com.bestway.domain.repositories.HomeRepository
import com.bestway.presentation.delegate.HomeDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
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
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val delegate: HomeDelegate,
) : ViewModel() {
    // Ui State properties
    private val _hotPosts: MutableStateFlow<PostsUiState> = MutableStateFlow(PostsUiState())
    var hotPosts: StateFlow<PostsUiState> = _hotPosts.asStateFlow()

    private val _newPosts: MutableStateFlow<PostsUiState> = MutableStateFlow(PostsUiState())
    var newPosts: StateFlow<PostsUiState> = _newPosts.asStateFlow()

    private val _topPosts: MutableStateFlow<PostsUiState> = MutableStateFlow(PostsUiState())
    var topPosts: StateFlow<PostsUiState> = _topPosts.asStateFlow()

    private val _bestPosts: MutableStateFlow<PostsUiState> = MutableStateFlow(PostsUiState())
    var bestPosts: StateFlow<PostsUiState> = _bestPosts.asStateFlow()

    private val _risingPosts: MutableStateFlow<PostsUiState> = MutableStateFlow(PostsUiState())
    var risingPosts: StateFlow<PostsUiState> = _risingPosts.asStateFlow()

    private val _controversialPosts: MutableStateFlow<PostsUiState> =
        MutableStateFlow(PostsUiState())
    var controversialPosts: StateFlow<PostsUiState> = _controversialPosts.asStateFlow()

    fun getHotPosts(
        shouldRefreshData: Boolean = false,
        nextPageKey: String? = "",
    ) {
        repository
            .getHotPosts(shouldRefreshData, nextPageKey)
            .onStart {
                if (!shouldRefreshData && nextPageKey.isNullOrEmpty()) {
                    _hotPosts.update { it.copy(isLoading = true) }
                }
            }.onEach { redditPosts ->
                _hotPosts.update {
                    it.copy(
                        data = redditPosts?.map { post -> post.asUiModel() },
                        isLoading = false,
                        errorMessage = if (redditPosts == null) "Data is not available" else null,
                        isDataRefreshed = shouldRefreshData,
                    )
                }
            }.catch { throwable ->
                Log.e(HomeViewModel::class.simpleName, throwable.message ?: "Unknown error")
                _hotPosts.update { it.copy(isLoading = false, errorMessage = throwable.message) }
            }.launchIn(viewModelScope)
    }

    fun getNewPosts(
        shouldRefreshData: Boolean = false,
        nextPageKey: String? = "",
    ) {
        repository
            .getNewPosts(shouldRefreshData, nextPageKey)
            .onStart {
                if (!shouldRefreshData && nextPageKey.isNullOrEmpty()) {
                    _newPosts.update { it.copy(isLoading = true) }
                }
            }.onEach { redditPosts ->
                redditPosts?.let { posts ->
                    _newPosts.update {
                        it.copy(
                            data = posts.map { post -> post.asUiModel() },
                            isLoading = false,
                            errorMessage = null,
                            isDataRefreshed = shouldRefreshData,
                        )
                    }
                }
            }.catch { throwable ->
                Log.e(HomeViewModel::class.simpleName, throwable.message ?: "Unknown error")
                _newPosts.update { it.copy(isLoading = false, errorMessage = throwable.message) }
            }.launchIn(viewModelScope)
    }

    fun getTopPosts(
        shouldRefreshData: Boolean = false,
        nextPageKey: String? = "",
    ) {
        repository
            .getTopPosts(shouldRefreshData, nextPageKey)
            .onStart {
                if (!shouldRefreshData && nextPageKey.isNullOrEmpty()) {
                    _topPosts.update { it.copy(isLoading = true) }
                }
            }.onEach { redditPosts ->
                redditPosts?.let { posts ->
                    _topPosts.update {
                        it.copy(
                            data = posts.map { post -> post.asUiModel() },
                            isLoading = false,
                            errorMessage = null,
                            isDataRefreshed = shouldRefreshData,
                        )
                    }
                }
            }.catch { throwable ->
                Log.e(HomeViewModel::class.simpleName, throwable.message ?: "Unknown error")
                _topPosts.update { it.copy(isLoading = false, errorMessage = throwable.message) }
            }.launchIn(viewModelScope)
    }

    fun getBestPosts(
        shouldRefreshData: Boolean = false,
        nextPageKey: String? = "",
    ) {
        repository
            .getBestPosts(shouldRefreshData, nextPageKey)
            .onStart {
                if (!shouldRefreshData && nextPageKey.isNullOrEmpty()) {
                    _bestPosts.update { it.copy(isLoading = true) }
                }
            }.onEach { redditPosts ->
                redditPosts?.let { posts ->
                    _bestPosts.update {
                        it.copy(
                            data = posts.map { post -> post.asUiModel() },
                            isLoading = false,
                            errorMessage = null,
                            isDataRefreshed = shouldRefreshData,
                        )
                    }
                }
            }.catch { throwable ->
                Log.e(HomeViewModel::class.simpleName, throwable.message ?: "Unknown error")

                _bestPosts.update { it.copy(isLoading = false, errorMessage = throwable.message) }
            }.launchIn(viewModelScope)
    }

    fun getRisingsPosts(
        shouldRefreshData: Boolean = false,
        nextPageKey: String? = "",
    ) {
        repository
            .getRisingPosts(shouldRefreshData, nextPageKey)
            .onStart {
                if (!shouldRefreshData && nextPageKey.isNullOrEmpty()) {
                    _risingPosts.update { it.copy(isLoading = true) }
                }
            }.onEach { redditPosts ->
                redditPosts?.let { posts ->
                    _risingPosts.update {
                        it.copy(
                            data = posts.map { post -> post.asUiModel() },
                            isLoading = false,
                            errorMessage = null,
                            isDataRefreshed = shouldRefreshData,
                        )
                    }
                }
            }.catch { throwable ->
                Log.e(HomeViewModel::class.simpleName, throwable.message ?: "Unknown error")
                _risingPosts.update { it.copy(isLoading = false, errorMessage = throwable.message) }
            }.launchIn(viewModelScope)
    }

    fun getControversialPosts(
        shouldRefreshData: Boolean = false,
        nextPageKey: String? = "",
    ) {
        repository
            .getControversialPosts(shouldRefreshData, nextPageKey)
            .onStart {
                if (!shouldRefreshData && nextPageKey.isNullOrEmpty()) {
                    _controversialPosts.update { it.copy(isLoading = true) }
                }
            }.onEach { redditPosts ->
                redditPosts?.let { posts ->
                    _controversialPosts.update {
                        it.copy(
                            data = posts.map { post -> post.asUiModel() },
                            isLoading = false,
                            errorMessage = null,
                            isDataRefreshed = shouldRefreshData,
                        )
                    }
                }
            }.catch { throwable ->
                Log.e(HomeViewModel::class.simpleName, throwable.message ?: "Unknown error")
                _controversialPosts.update {
                    it.copy(isLoading = false, errorMessage = throwable.message)
                }
            }.launchIn(viewModelScope)
    }

    fun onSaveIconClick(
        postId: String,
        onPostSaved: (Boolean) -> Unit,
    ) {
        viewModelScope.launch {
            val isPostSaved = repository.togglePostSavedStatus(postId)
            delegate.updateSavedPosts(isPostSaved, postId)
            updatePostSavedUiState(postId, isPostSaved)
            onPostSaved(isPostSaved)
        }
    }

    private fun updatePostSavedUiState(
        postId: String,
        isPostSaved: Boolean,
    ) {
        _hotPosts.update {
            it.copy(
                data =
                it.data?.map { post ->
                    if (post.id == postId) {
                        post.copy(isSaved = isPostSaved)
                    } else {
                        post
                    }
                },
            )
        }
    }
}

@Immutable
data class PostsUiState(
    val data: List<RedditPostUiModel>? = null,
    val isLoading: Boolean = false,
    val isDataRefreshed: Boolean = false,
    val errorMessage: String? = null,
)
