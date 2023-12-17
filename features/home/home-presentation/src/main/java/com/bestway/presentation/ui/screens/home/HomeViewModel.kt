package com.bestway.presentation.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestway.domain.repositories.HomeRepository
import com.bestway.models.listings.ListingsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

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


    // Exception Handling properties starts here
    private val hotPostsExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(HomeViewModel::class.simpleName, throwable.message ?: "Unknown error")

        _hotPosts.update { it.copy(isLoading = false, errorMessage = throwable.message) }
    }

    private val newPostsExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(HomeViewModel::class.simpleName, throwable.message ?: "Unknown error")

        _newPosts.update { it.copy(isLoading = false, errorMessage = throwable.message) }
    }

    private val topPostsExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(HomeViewModel::class.simpleName, throwable.message ?: "Unknown error")

        _topPosts.update { it.copy(isLoading = false, errorMessage = throwable.message) }
    }

    private val bestPostsExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(HomeViewModel::class.simpleName, throwable.message ?: "Unknown error")

        _bestPosts.update { it.copy(isLoading = false, errorMessage = throwable.message) }
    }

    private val risingPostsExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(HomeViewModel::class.simpleName, throwable.message ?: "Unknown error")

        _risingPosts.update { it.copy(isLoading = false, errorMessage = throwable.message) }
    }

    private val controversialPostsExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(HomeViewModel::class.simpleName, throwable.message ?: "Unknown error")

        _controversialPosts.update { it.copy(isLoading = false, errorMessage = throwable.message) }
    }
    // Exception Handling properties end here

    fun getHotPosts() {
        _hotPosts.update { it.copy(isLoading = true) }

        viewModelScope.launch(hotPostsExceptionHandler) {
            repository.getHotPosts().collectLatest { listingsResponse ->
                listingsResponse.data?.let { responseListingsData ->
                    _hotPosts.update {
                        it.copy(data = responseListingsData, isLoading = false, errorMessage = null)
                    }
                }
            }

            // stop the loading if the data is null
            _hotPosts.update { it.copy(isLoading = false) }
        }
    }

    fun getNewPosts() {
        _newPosts.update { it.copy(isLoading = true) }

        viewModelScope.launch(newPostsExceptionHandler) {
            repository.getNewPosts().collectLatest { listingsResponse ->
                listingsResponse.data?.let { responseListingsData ->
                    _newPosts.update {
                        it.copy(data = responseListingsData, isLoading = false, errorMessage = null)
                    }
                }
            }

            // stop the loading if the data is null
            _newPosts.update { it.copy(isLoading = false) }
        }
    }

    fun getTopPosts() {
        _topPosts.update { it.copy(isLoading = true) }

        viewModelScope.launch(topPostsExceptionHandler) {
            repository.getTopPosts().collectLatest { listingsResponse ->
                listingsResponse.data?.let { responseListingsData ->
                    _topPosts.update {
                        it.copy(data = responseListingsData, isLoading = false, errorMessage = null)
                    }
                }
            }

            // stop the loading if the data is null
            _topPosts.update { it.copy(isLoading = false) }
        }
    }

    fun getBestPosts() {
        _bestPosts.update { it.copy(isLoading = true) }

        viewModelScope.launch(bestPostsExceptionHandler) {
            repository.getBestPosts().collectLatest { listingsResponse ->
                listingsResponse.data?.let { responseListingsData ->
                    _bestPosts.update {
                        it.copy(data = responseListingsData, isLoading = false, errorMessage = null)
                    }
                }
            }

            // stop the loading if the data is null
            _bestPosts.update { it.copy(isLoading = false) }
        }
    }

    fun getRisingsPosts() {
        _risingPosts.update { it.copy(isLoading = true) }

        viewModelScope.launch(risingPostsExceptionHandler) {
            repository.getRisingPosts().collectLatest { listingsResponse ->
                listingsResponse.data?.let { responseListingsData ->
                    _risingPosts.update {
                        it.copy(data = responseListingsData, isLoading = false, errorMessage = null)
                    }
                }
            }

            // stop the loading if the data is null
            _risingPosts.update { it.copy(isLoading = false) }
        }

    }

    fun getControversialPosts() {
        _controversialPosts.update { it.copy(isLoading = true) }

        viewModelScope.launch(controversialPostsExceptionHandler) {
            repository.getControversialPosts().collectLatest { listingsResponse ->
                listingsResponse.data?.let { responseListingsData ->
                    _controversialPosts.update {
                        it.copy(data = responseListingsData, isLoading = false, errorMessage = null)
                    }
                }

                // stop the loading if the data is null
                _controversialPosts.update { it.copy(isLoading = false) }
            }
        }
    }
}

@Immutable
data class PostsUiState(
    val data: ListingsData? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
