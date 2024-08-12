package com.anmolsahi.presentation.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anmolsahi.commonui.mappers.asDomain
import com.anmolsahi.commonui.mappers.asUiModel
import com.anmolsahi.commonui.models.RedditPostUiModel
import com.anmolsahi.domain.delegate.SearchDelegate
import com.anmolsahi.domain.model.RecentSearch
import com.anmolsahi.domain.repositories.SearchRepository
import com.anmolsahi.domain.usecase.SearchRedditUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject
constructor(
    private val repository: SearchRepository,
    private val delegate: SearchDelegate,
    private val searchRedditUseCase: SearchRedditUseCase,
) : ViewModel() {
    private val _searchDataUiState = MutableStateFlow(SearchDataUiModel())
    val searchDataUiState: StateFlow<SearchDataUiModel> = _searchDataUiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _searchBarActive = MutableStateFlow(false)
    val searchBarActive = _searchBarActive.asStateFlow()

    init {
        @Suppress("OPT_IN_USAGE")
        _searchQuery
            .debounce(500)
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
            .onEach { query ->
                searchReddit(query)
            }
            .launchIn(viewModelScope)
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        if (_searchQuery.value.isEmpty()) {
            _searchDataUiState.update {
                it.copy(searchedData = null)
            }
        }
    }

    fun updateSearchBarActive(active: Boolean) {
        _searchBarActive.value = active
    }

    private fun searchReddit(query: String, nextPageKey: String? = null) {
        searchRedditUseCase(query, nextPageKey)
            .onStart {
                if (nextPageKey.isNullOrEmpty()) {
                    _searchDataUiState.update {
                        it.copy(
                            isLoading = true,
                            searchedData = null,
                        )
                    }
                }
            }
            .onEach { redditPosts ->
                _searchDataUiState.update {
                    it.copy(
                        searchedData = it.searchedData.orEmpty() +
                            redditPosts.orEmpty().map { post -> post.asUiModel() },
                        isLoading = false,
                    )
                }
            }
            .catch { throwable -> handleException(throwable) }
            .launchIn(viewModelScope)
    }

    fun getAllRecentSearches() {
        repository.getRecentSearches()
            .onStart { _searchDataUiState.update { it.copy(isLoading = true) } }
            .map { it.reversed() }
            .onEach { recentSearches ->
                _searchDataUiState.update {
                    it.copy(
                        isLoading = false,
                        recentSearches = recentSearches,
                    )
                }
            }
            .catch { throwable -> handleException(throwable) }
            .launchIn(viewModelScope)
    }

    fun onDeleteItemClick(recentSearch: RecentSearch) {
        viewModelScope.launch {
            repository.deleteRecentSearch(recentSearch)
        }
    }

    fun saveRecentSearch(recentSearch: String) {
        val recentSearchValue = recentSearch.lowercase().trim()
        if (_searchDataUiState.value.recentSearches.any { it.value == recentSearchValue }) {
            return
        }

        if (recentSearchValue.isBlank()) {
            return
        }

        viewModelScope.launch {
            repository.insertRecentSearch(RecentSearch(recentSearch))
        }
    }

    private fun handleException(throwable: Throwable) {
        Log.e(SearchViewModel::class.simpleName, throwable.message ?: "Unknown error")
        _searchDataUiState.update {
            it.copy(
                isLoading = false,
                errorMessage = throwable.message,
            )
        }
    }

    fun onBackClick(searchedValue: String) {
        saveRecentSearch(searchedValue)
        updateSearchQuery("")
    }

    fun onSaveIconClick(post: RedditPostUiModel) {
        viewModelScope.launch {
            _searchDataUiState.update {
                it.copy(
                    searchedData = it.searchedData?.map { redditPost ->
                        if (redditPost.id == post.id) {
                            redditPost.copy(isSaved = !post.isSaved)
                        } else {
                            redditPost
                        }
                    },
                )
            }
            delegate.savePost(post.asDomain())
        }
    }

    fun loadMoreData(nextPageKey: String?) {
        nextPageKey?.let {
            searchReddit(_searchQuery.value, nextPageKey)
        }
    }
}

data class SearchDataUiModel(
    val isLoading: Boolean = false,
    val recentSearches: List<RecentSearch> = emptyList(),
    val searchedData: List<RedditPostUiModel>? = null,
    val errorMessage: String? = null,
)
