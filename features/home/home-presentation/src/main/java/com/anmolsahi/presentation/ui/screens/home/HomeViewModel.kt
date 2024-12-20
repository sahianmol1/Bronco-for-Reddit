package com.anmolsahi.presentation.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anmolsahi.commonui.mappers.asUiModel
import com.anmolsahi.commonui.models.RedditPostUiModel
import com.anmolsahi.designsystem.uicomponents.HomePage
import com.anmolsahi.domain.repositories.HomeRepository
import com.anmolsahi.domain.repository.AppPreferencesRepository
import com.anmolsahi.domain.usecase.TogglePostSavedStatusUseCase
import com.anmolsahi.domain.usecase.UpdateSavedPostsUseCase
import com.anmolsahi.presentation.utils.asPostType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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

@SuppressWarnings("TooManyFunctions")
@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val togglePostSavedStatusUseCase: TogglePostSavedStatusUseCase,
    private val updateSavedPostsUseCase: UpdateSavedPostsUseCase,
    private val prefsRepository: AppPreferencesRepository,
) : ViewModel() {

    private companion object {
        const val TWELVE_HOURS_IN_MILLIS = 12 * 60 * 60 * 1000 // 12 hours
        const val SIX_HOURS_IN_MILLIS = 6 * 60 * 60 * 1000 // 6 hours
    }

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

    private val currentTime = System.currentTimeMillis()

    fun getHotPosts(
        shouldRefreshData: Boolean = false,
        nextPageKey: String? = "",
        silentUpdate: Boolean = false,
    ) {
        repository.getHotPosts(shouldRefreshData, nextPageKey)
            .onStart {
                if (shouldRefreshData) {
                    _hotPosts.update { it.copy(areNewPostsAvailable = false) }
                }
                if (shouldShowLoadingIndicator(shouldRefreshData, nextPageKey, silentUpdate)) {
                    _hotPosts.update { it.copy(isLoading = true) }
                }
            }.onEach { redditPosts ->
                _hotPosts.update {
                    it.copy(
                        data = redditPosts?.map { post -> post.asUiModel() },
                        isLoading = false,
                        errorMessage = if (redditPosts == null) "Data is not available" else null,
                        isPullRefreshLoading = false,
                    )
                }
            }.catch { throwable ->
                Log.e(HomeViewModel::class.simpleName, throwable.message ?: "Unknown error")
                _hotPosts.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = throwable.message,
                        isPullRefreshLoading = false,
                    )
                }
            }.launchIn(viewModelScope)
    }

    fun getNewPosts(
        shouldRefreshData: Boolean = false,
        nextPageKey: String? = "",
        silentUpdate: Boolean = false,
    ) {
        repository.getNewPosts(shouldRefreshData, nextPageKey)
            .onStart {
                if (shouldRefreshData) {
                    _newPosts.update { it.copy(areNewPostsAvailable = false) }
                }
                if (shouldShowLoadingIndicator(shouldRefreshData, nextPageKey, silentUpdate)) {
                    _newPosts.update { it.copy(isLoading = true) }
                }
            }.onEach { redditPosts ->
                redditPosts?.let { posts ->
                    _newPosts.update {
                        it.copy(
                            data = posts.map { post -> post.asUiModel() },
                            isLoading = false,
                            errorMessage = null,
                            isPullRefreshLoading = false,
                        )
                    }
                }
            }.catch { throwable ->
                Log.e(HomeViewModel::class.simpleName, throwable.message ?: "Unknown error")
                _newPosts.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = throwable.message,
                        isPullRefreshLoading = false,
                    )
                }
            }.launchIn(viewModelScope)
    }

    fun getTopPosts(
        shouldRefreshData: Boolean = false,
        nextPageKey: String? = "",
        silentUpdate: Boolean = false,
    ) {
        repository.getTopPosts(shouldRefreshData, nextPageKey)
            .onStart {
                if (shouldRefreshData) {
                    _topPosts.update { it.copy(areNewPostsAvailable = false) }
                }
                if (shouldShowLoadingIndicator(shouldRefreshData, nextPageKey, silentUpdate)) {
                    _topPosts.update { it.copy(isLoading = true) }
                }
            }.onEach { redditPosts ->
                redditPosts?.let { posts ->
                    _topPosts.update {
                        it.copy(
                            data = posts.map { post -> post.asUiModel() },
                            isLoading = false,
                            errorMessage = null,
                            isPullRefreshLoading = false,
                        )
                    }
                }
            }.catch { throwable ->
                Log.e(HomeViewModel::class.simpleName, throwable.message ?: "Unknown error")
                _topPosts.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = throwable.message,
                        isPullRefreshLoading = false,
                    )
                }
            }.launchIn(viewModelScope)
    }

    fun getBestPosts(
        shouldRefreshData: Boolean = false,
        nextPageKey: String? = "",
        silentUpdate: Boolean = false,
    ) {
        repository.getBestPosts(shouldRefreshData, nextPageKey)
            .onStart {
                if (shouldRefreshData) {
                    _bestPosts.update { it.copy(areNewPostsAvailable = false) }
                }
                if (shouldShowLoadingIndicator(shouldRefreshData, nextPageKey, silentUpdate)) {
                    _bestPosts.update { it.copy(isLoading = true) }
                }
            }.onEach { redditPosts ->
                redditPosts?.let { posts ->
                    _bestPosts.update {
                        it.copy(
                            data = posts.map { post -> post.asUiModel() },
                            isLoading = false,
                            errorMessage = null,
                            isPullRefreshLoading = false,
                        )
                    }
                }
            }.catch { throwable ->
                Log.e(HomeViewModel::class.simpleName, throwable.message ?: "Unknown error")

                _bestPosts.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = throwable.message,
                        isPullRefreshLoading = false,
                    )
                }
            }.launchIn(viewModelScope)
    }

    fun getRisingsPosts(
        shouldRefreshData: Boolean = false,
        nextPageKey: String? = "",
        silentUpdate: Boolean = false,
    ) {
        repository.getRisingPosts(shouldRefreshData, nextPageKey)
            .onStart {
                if (shouldRefreshData) {
                    _risingPosts.update { it.copy(areNewPostsAvailable = false) }
                }
                if (shouldShowLoadingIndicator(shouldRefreshData, nextPageKey, silentUpdate)) {
                    _risingPosts.update { it.copy(isLoading = true) }
                }
            }.onEach { redditPosts ->
                redditPosts?.let { posts ->
                    _risingPosts.update {
                        it.copy(
                            data = posts.map { post -> post.asUiModel() },
                            isLoading = false,
                            errorMessage = null,
                            isPullRefreshLoading = false,
                        )
                    }
                }
            }.catch { throwable ->
                Log.e(HomeViewModel::class.simpleName, throwable.message ?: "Unknown error")
                _risingPosts.update {
                    it.copy(
                        isLoading = false,
                        isPullRefreshLoading = false,
                        errorMessage = throwable.message,
                    )
                }
            }.launchIn(viewModelScope)
    }

    fun getControversialPosts(
        shouldRefreshData: Boolean = false,
        nextPageKey: String? = "",
        silentUpdate: Boolean = false,
    ) {
        repository.getControversialPosts(shouldRefreshData, nextPageKey)
            .onStart {
                if (shouldRefreshData) {
                    _controversialPosts.update { it.copy(areNewPostsAvailable = false) }
                }
                if (shouldShowLoadingIndicator(shouldRefreshData, nextPageKey, silentUpdate)) {
                    _controversialPosts.update { it.copy(isLoading = true) }
                }
            }.onEach { redditPosts ->
                redditPosts?.let { posts ->
                    _controversialPosts.update {
                        it.copy(
                            data = posts.map { post -> post.asUiModel() },
                            isLoading = false,
                            errorMessage = null,
                            isPullRefreshLoading = false,
                        )
                    }
                }
            }.catch { throwable ->
                Log.e(HomeViewModel::class.simpleName, throwable.message ?: "Unknown error")
                _controversialPosts.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = throwable.message,
                        isPullRefreshLoading = false,
                    )
                }
            }.launchIn(viewModelScope)
    }

    fun onSaveIconClick(postId: String, homePageType: HomePage, onPostSaved: (Boolean) -> Unit) {
        viewModelScope.launch {
            val postType = homePageType.asPostType()
            val isPostSaved = togglePostSavedStatusUseCase(postId, postType)
            updateSavedPostsUseCase(postId, isPostSaved, postType)
            updatePostSavedUiState(postId, isPostSaved, homePageType)
            onPostSaved(isPostSaved)
        }
    }

    fun checkAndForceRefreshPosts() {
        viewModelScope.launch {
            /**
             * Force refresh if stale data is present in the database
             *
             * Get the timestamp from the datastore as soon as the viewmodel is launched
             * and decide if force refresh is needed
             */
            listOf(
                Pair(prefsRepository.getHotPostsTimestamp(), _hotPosts),
                Pair(prefsRepository.getTopPostsTimestamp(), _topPosts),
                Pair(prefsRepository.getNewPostsTimestamp(), _newPosts),
                Pair(prefsRepository.getBestPostsTimestamp(), _bestPosts),
                Pair(prefsRepository.getRisingPostsTimestamp(), _risingPosts),
                Pair(prefsRepository.getControversialPostsTimestamp(), _controversialPosts),
            ).forEach { (timestampFlow, uiStateFlow) ->
                checkAndForceRefreshPosts(timestampFlow, uiStateFlow)
            }
        }
    }

    fun onPullRefresh(homePage: HomePage) {
        when (homePage) {
            HomePage.HOT -> _hotPosts.update { it.copy(isPullRefreshLoading = true) }
            HomePage.NEW -> _newPosts.update { it.copy(isPullRefreshLoading = true) }
            HomePage.TOP -> _topPosts.update { it.copy(isPullRefreshLoading = true) }
            HomePage.BEST -> _bestPosts.update { it.copy(isPullRefreshLoading = true) }
            HomePage.RISING -> _risingPosts.update { it.copy(isPullRefreshLoading = true) }
            HomePage.CONTROVERSIAL -> _controversialPosts.update {
                it.copy(isPullRefreshLoading = true)
            }
        }
    }

    private fun updatePostSavedUiState(
        postId: String,
        isPostSaved: Boolean,
        homePageType: HomePage,
    ) {
        when (homePageType) {
            HomePage.HOT -> _hotPosts.updatePostSavedState(postId, isPostSaved)

            HomePage.NEW -> _newPosts.updatePostSavedState(postId, isPostSaved)

            HomePage.TOP -> _topPosts.updatePostSavedState(postId, isPostSaved)

            HomePage.BEST -> _bestPosts.updatePostSavedState(postId, isPostSaved)

            HomePage.RISING -> _risingPosts.updatePostSavedState(postId, isPostSaved)

            HomePage.CONTROVERSIAL -> _controversialPosts.updatePostSavedState(postId, isPostSaved)
        }
    }

    private fun shouldShowLoadingIndicator(
        shouldRefreshData: Boolean,
        nextPageKey: String?,
        silentUpdate: Boolean,
    ): Boolean = !shouldRefreshData && nextPageKey.isNullOrEmpty() && !silentUpdate

    private fun checkAndForceRefreshPosts(
        timestampFlow: Flow<Long>,
        stateFlow: MutableStateFlow<PostsUiState>,
    ) {
        viewModelScope.launch {
            timestampFlow.collect { prefsTimestamp ->
                val timeDifference = currentTime - prefsTimestamp
                if (timeDifference in SIX_HOURS_IN_MILLIS..TWELVE_HOURS_IN_MILLIS) {
                    stateFlow.update { it.copy(areNewPostsAvailable = true) }
                } else if (timeDifference > TWELVE_HOURS_IN_MILLIS) {
                    stateFlow.update { it.copy(isPullRefreshLoading = true) }
                }
            }
        }
    }

    private fun MutableStateFlow<PostsUiState>.updatePostSavedState(
        postId: String,
        isPostSaved: Boolean,
    ) {
        update {
            it.copy(
                data = it.data?.map { post ->
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
internal data class PostsUiState(
    val data: List<RedditPostUiModel>? = null,
    val isLoading: Boolean = false,
    val isPullRefreshLoading: Boolean = false,
    val areNewPostsAvailable: Boolean = false,
    val errorMessage: String? = null,
)
