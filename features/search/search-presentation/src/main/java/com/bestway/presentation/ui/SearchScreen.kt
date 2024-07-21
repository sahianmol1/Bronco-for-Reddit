package com.bestway.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anmolsahi.common_ui.components.PostComponent
import com.anmolsahi.common_ui.utils.ErrorDialog
import com.anmolsahi.common_ui.utils.isScrollingUp
import com.bestway.design_system.ui_components.BRLinearProgressIndicator
import com.bestway.design_system.ui_components.BRSearchBar
import com.bestway.domain.model.RecentSearch
import com.bestway.presentation.ui.components.QuickSearchPostComponent
import com.bestway.presentation.ui.components.RecentSearchesComponent
import com.bestway.search_presentation.R

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    onPostClick: (postId: String, postUrl: String) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    val uiState by viewModel.searchDataUiState.collectAsStateWithLifecycle()
    val searchedValue by viewModel.searchQuery.collectAsStateWithLifecycle()
    var showErrorDialog by remember { mutableStateOf(false) }
    val searchedData by remember(uiState) { mutableStateOf(uiState.searchedData.orEmpty()) }
    var searchBarActive by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getAllRecentSearches()
    }

    LaunchedEffect(uiState.errorMessage) {
        if (!uiState.errorMessage.isNullOrEmpty()) {
            showErrorDialog = true
        }
    }

    // TODO: Back Handler not working
//    BackHandler(searchBarActive) {
//        if (searchedValue.isNotEmpty()) {
//            viewModel.saveRecentSearch(RecentSearch(value = searchedValue))
//        }
//        viewModel.updateSearchQuery("")
//        searchBarActive = false
//    }

    Column {
        AnimatedVisibility(lazyListState.isScrollingUp()) {
            BRSearchBar(
                modifier = modifier,
                query = searchedValue,
                active = searchBarActive,
                onActiveChange = {
                    searchBarActive = it
                },
                onQueryChange = {
                    viewModel.updateSearchQuery(it)
                },
                onSearch = {
                    viewModel.saveRecentSearch(RecentSearch(value = searchedValue))
                },
                onBack = {
                    viewModel.saveRecentSearch(RecentSearch(value = searchedValue))
                    viewModel.updateSearchQuery("")
                }
            ) {
                if (searchedValue.isEmpty() && uiState.recentSearches.isNotEmpty() && !uiState.isLoading && uiState.errorMessage.isNullOrEmpty()) {
                    RecentSearchesComponent(
                        modifier = Modifier,
                        recentSearches = uiState.recentSearches,
                        onItemClick = { recentSearch ->
                            viewModel.updateSearchQuery(recentSearch.value)
                        },
                        onDeleteItemClick = { recentSearch ->
                            viewModel.onDeleteItemClick(recentSearch)
                        },
                    )
                }

                if (searchedData.isNotEmpty() && !uiState.isLoading && searchedValue.isNotEmpty()) {
                    val quickData = searchedData.take(10)
                    LazyColumn {
                        item(
                            contentType = { "quick_results_header" }
                        ) {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = stringResource(R.string.quick_results),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                    )

                                    Text(
                                        modifier = Modifier.clickable {
                                            viewModel.saveRecentSearch(RecentSearch(value = searchedValue))
                                            searchBarActive = false
                                        },
                                        text = stringResource(R.string.see_all_results),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.primary,
                                        textDecoration = TextDecoration.Underline,
                                    )
                                }
                            }
                        }

                        items(
                            count = quickData.size,
                            key = { index -> quickData[index].id },
                            contentType = { "quick_searched_post" }
                        ) { index ->
                            QuickSearchPostComponent(
                                redditPostUiModel = quickData[index],
                                onPostClick = { id, url ->
                                    onPostClick(id, url)
                                },
                            )
                        }

                        item(
                            contentType = { "quick_results_footer" }
                        ) {
                            HorizontalDivider(
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            OutlinedButton(
                                modifier = Modifier
                                    .padding(vertical = 8.dp, horizontal = 16.dp)
                                    .fillMaxWidth(),
                                onClick = {
                                    viewModel.saveRecentSearch(RecentSearch(value = searchedValue))
                                    searchBarActive = false
                                },
                            ) {
                                Text(text = stringResource(R.string.view_all_posts))
                            }
                        }
                    }
                }

                if (uiState.isLoading) {
                    BRLinearProgressIndicator()
                }
            }
        }

        if (searchedData.isNotEmpty()) {
            LazyColumn(state = lazyListState,) {
                items(
                    count = searchedData.size,
                    key = { index -> searchedData[index].id },
                    contentType = { "reddit_post" }
                ) { index ->
                    PostComponent(
                        redditPostUiModel = searchedData[index],
                        onClick = onPostClick,
                        onSaveIconClick = {
                            viewModel.onSaveIconClick(searchedData[index])
                        },
                    )
                }
            }
        }

        if (uiState.isLoading) {
            BRLinearProgressIndicator()
        }

        if (showErrorDialog) {
            ErrorDialog(
                uiState.errorMessage.orEmpty(),
                onConfirmButtonClick = { showErrorDialog = false },
            )
        }
    }
}
