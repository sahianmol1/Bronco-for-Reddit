package com.bestway.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
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
import com.anmolsahi.common_ui.utils.isScrollingUp
import com.bestway.design_system.ui_components.BRSearchBar
import com.bestway.domain.model.RecentSearch
import com.bestway.presentation.ui.components.RecentSearchesComponent

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val lazyListState = rememberLazyListState()
    val searchDataUiState by viewModel.searchDataUiModel.collectAsStateWithLifecycle()
    var searchedValue by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getAllRecentSearches()
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
                if (searchDataUiState.recentSearches.isNotEmpty()) {
                    RecentSearchesComponent(
                        modifier = Modifier,
                        recentSearches = searchDataUiState.recentSearches,
                        onItemClick = { recentSearch ->
                            searchedValue = recentSearch.value
                        },
                        onDeleteItemClick = { recentSearch ->
                            viewModel.onDeleteItemClick(recentSearch)
                        },
                    )
                }
            }
        }

//        if (searchDataUiState.recentSearches.isNotEmpty()) {
//            RecentSearchesComponent(
//                modifier = Modifier,
//                lazyListState = lazyListState,
//                recentSearches = searchDataUiState.recentSearches,
//                onItemClick = { recentSearch ->
//                    searchedValue = recentSearch.value
//                },
//                onDeleteItemClick = { recentSearch ->
//                    viewModel.onDeleteItemClick(recentSearch)
//                },
//            )
//        }
    }
}
