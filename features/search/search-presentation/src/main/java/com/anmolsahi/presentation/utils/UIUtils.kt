package com.anmolsahi.presentation.utils

import com.anmolsahi.commonui.models.RedditPostUiModel
import com.anmolsahi.presentation.ui.SearchDataUiModel

internal fun shouldShowRecentSearches(searchedValue: String, uiState: SearchDataUiModel): Boolean {
    return searchedValue.isEmpty() &&
        uiState.recentSearches.isNotEmpty() &&
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
