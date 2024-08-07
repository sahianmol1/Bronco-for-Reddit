package com.anmolsahi.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anmolsahi.commonui.components.PostComponent
import com.anmolsahi.commonui.utils.ErrorDialog
import com.anmolsahi.commonui.utils.isScrollingUp
import com.anmolsahi.commonui.utils.scrollToTop
import com.anmolsahi.commonui.utils.shareRedditPost
import com.anmolsahi.designsystem.uicomponents.BRLinearProgressIndicator
import com.anmolsahi.designsystem.uicomponents.BRSearchBar
import com.anmolsahi.domain.model.RecentSearch
import com.anmolsahi.presentation.ui.SearchScreenDefaults.LOADING_INDICATOR
import com.anmolsahi.presentation.ui.SearchScreenDefaults.QUICK_RESULTS_FOOTER
import com.anmolsahi.presentation.ui.SearchScreenDefaults.QUICK_RESULTS_HEADER
import com.anmolsahi.presentation.ui.SearchScreenDefaults.QUICK_SEARCH_POST
import com.anmolsahi.presentation.ui.SearchScreenDefaults.QUICK_SEARCH_RESULTS_MAX_ITEMS
import com.anmolsahi.presentation.ui.SearchScreenDefaults.REDDIT_POST
import com.anmolsahi.presentation.ui.components.QuickSearchPostComponent
import com.anmolsahi.presentation.ui.components.RecentSearchesComponent
import com.anmolsahi.presentation.utils.shouldShowQuickResults
import com.anmolsahi.presentation.utils.shouldShowRecentSearches
import com.anmolsahi.searchpresentation.R

private object SearchScreenDefaults {
    const val QUICK_RESULTS_HEADER = "quick_results_header"
    const val QUICK_SEARCH_POST = "quick_searched_post"
    const val QUICK_RESULTS_FOOTER = "quick_results_footer"
    const val REDDIT_POST = "reddit_post"
    const val LOADING_INDICATOR = "loading_indicator"
    const val QUICK_SEARCH_RESULTS_MAX_ITEMS = 10
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    onPostClick: (postId: String, postUrl: String) -> Unit,
    onFullScreenIconClick: (videoUrl: String?) -> Unit,
) {
    val context = LocalContext.current
    val lazyListState = rememberLazyListState()
    val uiState by viewModel.searchDataUiState.collectAsStateWithLifecycle()
    val searchedValue by viewModel.searchQuery.collectAsStateWithLifecycle()
    var showErrorDialog by remember { mutableStateOf(false) }
    val searchedItemsList by remember(uiState) { mutableStateOf(uiState.searchedData.orEmpty()) }
    val searchBarActive by viewModel.searchBarActive.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getAllRecentSearches()
    }

    LaunchedEffect(uiState.errorMessage) {
        if (!uiState.errorMessage.isNullOrEmpty()) {
            showErrorDialog = true
        }
    }

    LaunchedEffect(uiState.isLoading) {
        if (uiState.isLoading && searchedItemsList.isEmpty()) {
            lazyListState.scrollToTop()
        }
    }

    LaunchedEffect(lazyListState.canScrollForward) {
        if (!lazyListState.canScrollForward && searchedItemsList.isNotEmpty()) {
            viewModel.loadMoreData(searchedItemsList.last().after)
        }
    }

    if (searchBarActive) {
        BackHandler {
            viewModel.onBackClick(searchedValue)
            viewModel.updateSearchBarActive(false)
        }
    }

    Column {
        AnimatedVisibility(lazyListState.isScrollingUp()) {
            BRSearchBar(
                modifier = modifier,
                query = searchedValue,
                active = searchBarActive,
                onActiveChange = {
                    viewModel.updateSearchBarActive(it)
                },
                onQueryChange = {
                    viewModel.updateSearchQuery(it)
                },
                onSearch = {
                    viewModel.saveRecentSearch(RecentSearch(value = searchedValue))
                },
                onBack = {
                    viewModel.onBackClick(searchedValue)
                },
            ) {
                SearchBarContentView(
                    uiState = uiState,
                    searchedValue = searchedValue,
                    onSeeAllResultsClick = {
                        viewModel.saveRecentSearch(RecentSearch(value = searchedValue))
                        viewModel.updateSearchBarActive(false)
                    },
                    onViewAllPostsClick = {
                        viewModel.saveRecentSearch(RecentSearch(value = searchedValue))
                        viewModel.updateSearchBarActive(false)
                    },
                    onRecentItemClick = { recentSearch ->
                        viewModel.updateSearchQuery(recentSearch.value)
                    },
                    onRecentItemDeleteClick = { recentSearch ->
                        viewModel.onDeleteItemClick(recentSearch)
                    },
                    onQuickSearchPostClick = { id, url ->
                        viewModel.saveRecentSearch(RecentSearch(value = searchedValue))
                        onPostClick(id, url)
                    },
                )
            }
        }

        if (searchedItemsList.isNotEmpty()) {
            LazyColumn(state = lazyListState, horizontalAlignment = Alignment.CenterHorizontally) {
                items(
                    count = searchedItemsList.size,
                    key = { index -> searchedItemsList[index].id },
                    contentType = { REDDIT_POST },
                ) { index ->
                    PostComponent(
                        redditPostUiModel = searchedItemsList[index],
                        onClick = { id, url ->
                            viewModel.saveRecentSearch(RecentSearch(value = searchedValue))
                            onPostClick(id, url)
                        },
                        onSaveIconClick = {
                            viewModel.onSaveIconClick(searchedItemsList[index])
                        },
                        onShareIconClick = { postUrl -> shareRedditPost(postUrl, context) },
                        onFullScreenIconClick = onFullScreenIconClick,
                    )
                }

                if (!searchedItemsList.last().after.isNullOrEmpty()) {
                    item(contentType = LOADING_INDICATOR) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(16.dp),
                        )
                    }
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

@Composable
fun SearchBarContentView(
    uiState: SearchDataUiModel,
    searchedValue: String,
    onSeeAllResultsClick: () -> Unit,
    onViewAllPostsClick: () -> Unit,
    onRecentItemClick: (RecentSearch) -> Unit,
    onRecentItemDeleteClick: (RecentSearch) -> Unit,
    onQuickSearchPostClick: (id: String, url: String) -> Unit,
) {
    val searchedData by remember(uiState) { mutableStateOf(uiState.searchedData.orEmpty()) }

    if (shouldShowRecentSearches(searchedValue, uiState)) {
        RecentSearchesComponent(
            modifier = Modifier,
            recentSearches = uiState.recentSearches,
            onItemClick = onRecentItemClick,
            onDeleteItemClick = onRecentItemDeleteClick,
        )
    }

    if (shouldShowQuickResults(searchedData, uiState, searchedValue)) {
        val quickData = searchedData.take(QUICK_SEARCH_RESULTS_MAX_ITEMS)
        LazyColumn {
            item(
                contentType = { QUICK_RESULTS_HEADER },
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
                                onSeeAllResultsClick()
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
                contentType = { QUICK_SEARCH_POST },
            ) { index ->
                QuickSearchPostComponent(
                    redditPostUiModel = quickData[index],
                    onPostClick = onQuickSearchPostClick,
                )
            }

            item(
                contentType = { QUICK_RESULTS_FOOTER },
            ) {
                HorizontalDivider(
                    modifier = Modifier.padding(top = 8.dp),
                )
                OutlinedButton(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .fillMaxWidth(),
                    onClick = onViewAllPostsClick,
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
