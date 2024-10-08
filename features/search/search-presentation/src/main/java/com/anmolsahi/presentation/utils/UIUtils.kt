package com.anmolsahi.presentation.utils

import com.anmolsahi.commonui.models.RedditPostUiModel
import com.anmolsahi.domain.model.RecentSearch
import com.anmolsahi.presentation.ui.searchscreen.SearchDataUiModel

internal fun shouldShowRecentSearches(
    searchedValue: String,
    uiState: SearchDataUiModel,
    recentSearches: List<RecentSearch>,
): Boolean {
    return searchedValue.isEmpty() &&
        recentSearches.isNotEmpty() &&
        !uiState.isLoading &&
        uiState.errorMessage.isNullOrEmpty()
}

internal fun shouldShowQuickResults(
    searchedData: List<RedditPostUiModel>,
    uiState: SearchDataUiModel,
    searchedValue: String,
): Boolean {
    return searchedData.isNotEmpty() && !uiState.isLoading && searchedValue.isNotEmpty()
}
