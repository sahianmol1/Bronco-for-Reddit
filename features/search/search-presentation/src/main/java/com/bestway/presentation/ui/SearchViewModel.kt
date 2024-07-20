package com.bestway.presentation.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anmolsahi.common_ui.models.RedditPostUiModel
import com.anmolsahi.common_ui.models.asUiModel
import com.bestway.domain.model.RecentSearch
import com.bestway.domain.repositories.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository,
) : ViewModel() {

    private val _searchDataUiModel = MutableStateFlow(SearchDataUiModel())
    val searchDataUiModel: StateFlow<SearchDataUiModel> = _searchDataUiModel.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    init {
        @Suppress("OPT_IN_USAGE")
        _searchQuery
            .debounce(500)
            .filter {
                return@filter it.trim().isNotEmpty()
            }
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
            .onEach { query ->
                searchReddit(query)
            }
            .launchIn(viewModelScope)
    }

    private fun searchReddit(query: String, nextPageKey: String? = null) {
        repository.searchReddit(query, nextPageKey)
            .onStart { _searchDataUiModel.update { it.copy(isLoading = true) } }
            .onEach { redditPosts ->
                _searchDataUiModel.update {
                    it.copy(
                        isLoading = false,
                        searchedData = redditPosts?.map { post -> post.asUiModel() }
                    )
                }
            }
            .catch { throwable -> handleException(throwable) }
            .launchIn(viewModelScope)
    }

    fun getAllRecentSearches() {
        repository.getRecentSearches()
            .onStart { _searchDataUiModel.update { it.copy(isLoading = true) } }
            .map { it.reversed() }
            .onEach { recentSearches ->
                _searchDataUiModel.update {
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

    fun onSearch(recentSearch: RecentSearch) {
        viewModelScope.launch {
            if (_searchDataUiModel.value.recentSearches.any { it.value == recentSearch.value }) {
                return@launch
            }

            repository.insertRecentSearch(recentSearch)
        }
    }

    private fun handleException(throwable: Throwable) {
        Log.e(SearchViewModel::class.simpleName, throwable.message ?: "Unknown error")
        _searchDataUiModel.update { it.copy(isLoading = false, errorMessage = throwable.message) }
    }
}

data class SearchDataUiModel(
    val isLoading: Boolean = false,
    val recentSearches: List<RecentSearch> = emptyList(),
    val searchedData: List<RedditPostUiModel>? = null,
    val errorMessage: String? = null,
)