package com.bestway.presentation.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestway.domain.repositories.HomeRepository
import com.bestway.presentation.model.RedditPostUiModel
import com.bestway.presentation.model.asUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
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

    fun getHotPosts() {
        repository
            .getHotPosts()
            .onStart {
                _hotPosts.update { it.copy(isLoading = true) }
            }
            .onEach { redditPosts ->
                _hotPosts.update {
                    it.copy(
                        data = redditPosts?.map { post -> post.asUiModel() },
                        isLoading = false,
                        errorMessage = if (redditPosts == null) "Data is not available" else null
                    )
                }
            }
            .catch { throwable ->
                Log.e(HomeViewModel::class.simpleName, throwable.message ?: "Unknown error")
                _hotPosts.update { it.copy(isLoading = false, errorMessage = throwable.message) }
            }
            .launchIn(viewModelScope)
    }

    fun getNewPosts() {
        repository
            .getNewPosts()
            .onStart { _newPosts.update { it.copy(isLoading = true) } }
            .onEach { redditPosts ->
                redditPosts?.let { posts ->
                    _newPosts.update {
                        it.copy(
                            data = posts.map { post -> post.asUiModel() },
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
            }
            .catch { throwable ->
                Log.e(HomeViewModel::class.simpleName, throwable.message ?: "Unknown error")
                _newPosts.update { it.copy(isLoading = false, errorMessage = throwable.message) }
            }
            .launchIn(viewModelScope)
    }

    fun getTopPosts() {
        repository
            .getTopPosts()
            .onStart { _topPosts.update { it.copy(isLoading = true) } }
            .onEach { redditPosts ->
                redditPosts?.let { posts ->
                    _topPosts.update {
                        it.copy(
                            data = posts.map { post -> post.asUiModel() },
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
            }
            .catch { throwable ->
                Log.e(HomeViewModel::class.simpleName, throwable.message ?: "Unknown error")
                _topPosts.update { it.copy(isLoading = false, errorMessage = throwable.message) }
            }
            .launchIn(viewModelScope)
    }

    fun getBestPosts() {
        repository
            .getBestPosts()
            .onStart { _bestPosts.update { it.copy(isLoading = true) } }
            .onEach { redditPosts ->
                redditPosts?.let { posts ->
                    _bestPosts.update {
                        it.copy(
                            data = posts.map { post -> post.asUiModel() },
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
            }
            .catch { throwable ->
                Log.e(HomeViewModel::class.simpleName, throwable.message ?: "Unknown error")

                _bestPosts.update { it.copy(isLoading = false, errorMessage = throwable.message) }
            }
            .launchIn(viewModelScope)
    }

    fun getRisingsPosts() {
        repository
            .getRisingPosts()
            .onStart { _risingPosts.update { it.copy(isLoading = true) } }
            .onEach { redditPosts ->
                redditPosts?.let { posts ->
                    _risingPosts.update {
                        it.copy(
                            data = posts.map { post -> post.asUiModel() },
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
            }
            .catch { throwable ->
                Log.e(HomeViewModel::class.simpleName, throwable.message ?: "Unknown error")
                _risingPosts.update { it.copy(isLoading = false, errorMessage = throwable.message) }
            }
            .launchIn(viewModelScope)
    }

    fun getControversialPosts() {
        repository
            .getControversialPosts()
            .onStart { _controversialPosts.update { it.copy(isLoading = true) } }
            .onEach { redditPosts ->
                redditPosts?.let { posts ->
                    _controversialPosts.update {
                        it.copy(
                            data = posts.map { post -> post.asUiModel() },
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
            }
            .catch { throwable ->
                Log.e(HomeViewModel::class.simpleName, throwable.message ?: "Unknown error")
                _controversialPosts.update {
                    it.copy(isLoading = false, errorMessage = throwable.message)
                }
            }
            .launchIn(viewModelScope)
    }
}

@Immutable
data class PostsUiState(
    val data: List<RedditPostUiModel>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
