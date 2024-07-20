package com.bestway.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anmolsahi.common_ui.components.PostComponent
import com.anmolsahi.common_ui.utils.ErrorDialog
import com.anmolsahi.common_ui.utils.isScrollingUp
import com.bestway.design_system.ui_components.BRLinearProgressIndicator
import com.bestway.design_system.ui_components.BRSearchBar
import com.bestway.domain.model.RecentSearch
import com.bestway.presentation.ui.components.RecentSearchesComponent
import com.bestway.presentation.ui.components.SearchPostComponent

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val lazyListState = rememberLazyListState()
    val uiState by viewModel.searchDataUiModel.collectAsStateWithLifecycle()
    var searchedValue by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }
    val searchedData by remember(uiState) { mutableStateOf(uiState.searchedData.orEmpty()) }

    LaunchedEffect(Unit) {
        viewModel.getAllRecentSearches()
    }

    LaunchedEffect(searchedValue) {
        if (searchedValue.isNotEmpty()) {
            viewModel.searchReddit(query = searchedValue)
        }
    }

    Column {
        AnimatedVisibility(lazyListState.isScrollingUp()) {
            BRSearchBar(
                modifier = modifier,
                query = searchedValue,
                onQueryChange = { searchedValue = it },
                onSearch = {
                    if (searchedValue.isNotEmpty()) {
                        viewModel.onSearch(RecentSearch(value = searchedValue))
                    }
                },
                onBack = {
                    searchedValue = ""
                }
            ) {
                if (searchedValue.isEmpty() && uiState.recentSearches.isNotEmpty() && !uiState.isLoading && uiState.errorMessage.isNullOrEmpty()) {
                    RecentSearchesComponent(
                        modifier = Modifier,
                        recentSearches = uiState.recentSearches,
                        onItemClick = { recentSearch ->
                            searchedValue = recentSearch.value
                        },
                        onDeleteItemClick = { recentSearch ->
                            viewModel.onDeleteItemClick(recentSearch)
                        },
                    )
                }

                if (searchedData.isNotEmpty() && !uiState.isLoading && searchedValue.isNotEmpty()) {
                    val quickData = searchedData.take(10)
                    LazyColumn {
                        items(
                            count = quickData.size,
                            key = { index -> quickData[index].id },
                            contentType = { "quick_searched_post" }
                        ) { index ->
                            SearchPostComponent(
                                quickData[index]
                            )
                        }
                    }
                }

                if (uiState.isLoading) {
                    BRLinearProgressIndicator()
                }

                if (!uiState.errorMessage.isNullOrEmpty()) {
                    showErrorDialog = true
                }

                if (showErrorDialog) {
                    ErrorDialog(
                        uiState.errorMessage.orEmpty(),
                        onConfirmButtonClick = { showErrorDialog = false },
                    )
                }
            }
        }

        if (searchedData.isNotEmpty()) {
            LazyColumn(
                state = lazyListState,
            ) {
                items(
                    count = searchedData.size,
                    key = { index -> searchedData[index].id },
                    contentType = { "reddit_post" }
                ) { index ->
                    PostComponent(
                        redditPostUiModel = searchedData[index],
                        onClick = {},
                        onSaveIconClick = {}
                    )
                }
            }
        }

        if (uiState.isLoading) {
            BRLinearProgressIndicator()
        }

        if (!uiState.errorMessage.isNullOrEmpty()) {
            showErrorDialog = true
        }

        if (showErrorDialog) {
            ErrorDialog(
                uiState.errorMessage.orEmpty(),
                onConfirmButtonClick = { showErrorDialog = false },
            )
        }
    }
}
