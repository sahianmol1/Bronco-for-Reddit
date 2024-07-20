package com.bestway.presentation.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestway.domain.model.RecentSearch
import com.bestway.domain.repositories.SearchRepository
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
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository,
) : ViewModel() {

    private val _searchDataUiModel = MutableStateFlow(SearchDataUiModel())
    val searchDataUiModel: StateFlow<SearchDataUiModel> = _searchDataUiModel.asStateFlow()

    fun getAllRecentSearches() {
        repository.getRecentSearches()
            .onStart { _searchDataUiModel.update { it.copy(isLoading = true) } }
            .onEach { recentSearches ->
                _searchDataUiModel.update {
                    it.copy(
                        isLoading = false,
                        recentSearches = recentSearches,
                    )
                }
            }
            .catch { throwable ->
                Log.e(SearchViewModel::class.simpleName, throwable.message ?: "Unknown error")
                _searchDataUiModel.update { it.copy(isLoading = false) }
            }
            .launchIn(viewModelScope)
    }

    fun onDeleteItemClick(recentSearch: RecentSearch) {
        viewModelScope.launch {
            repository.deleteRecentSearch(recentSearch)
        }
    }

    fun onSearch(recentSearch: RecentSearch) {
        viewModelScope.launch {
            repository.insertRecentSearch(recentSearch)
        }
    }

    fun onBack(recentSearch: RecentSearch) {
        onSearch(recentSearch)
    }
}

data class SearchDataUiModel(
    val isLoading: Boolean = false,
    val recentSearches: List<RecentSearch> = emptyList(),
)