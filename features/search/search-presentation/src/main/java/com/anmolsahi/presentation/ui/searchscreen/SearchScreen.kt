package com.anmolsahi.presentation.ui.searchscreen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.exoplayer.ExoPlayer
import com.anmolsahi.commonui.components.PostComponent
import com.anmolsahi.commonui.utils.ErrorDialog
import com.anmolsahi.commonui.utils.ScrollHelper
import com.anmolsahi.commonui.utils.determineCurrentlyPlayingItem
import com.anmolsahi.commonui.utils.isScrollingUp
import com.anmolsahi.commonui.utils.scrollToTop
import com.anmolsahi.commonui.utils.shareRedditPost
import com.anmolsahi.designsystem.uicomponents.BRLinearProgressIndicator
import com.anmolsahi.designsystem.uicomponents.BRSearchBar
import com.anmolsahi.designsystem.utils.slideInFromTop
import com.anmolsahi.designsystem.utils.slideOutToTop2
import com.anmolsahi.domain.model.RecentSearch
import com.anmolsahi.presentation.ui.components.QuickSearchPostComponent
import com.anmolsahi.presentation.ui.components.RecentSearchesComponent
import com.anmolsahi.presentation.ui.searchscreen.SearchScreenDefaults.LOADING_INDICATOR
import com.anmolsahi.presentation.ui.searchscreen.SearchScreenDefaults.QUICK_RESULTS_FOOTER
import com.anmolsahi.presentation.ui.searchscreen.SearchScreenDefaults.QUICK_RESULTS_HEADER
import com.anmolsahi.presentation.ui.searchscreen.SearchScreenDefaults.QUICK_SEARCH_POST
import com.anmolsahi.presentation.ui.searchscreen.SearchScreenDefaults.QUICK_SEARCH_RESULTS_MAX_ITEMS
import com.anmolsahi.presentation.ui.searchscreen.SearchScreenDefaults.REDDIT_POST
import com.anmolsahi.presentation.ui.searchscreen.SearchScreenDefaults.SEARCH_BAR_HEIGHT
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
    const val SEARCH_BAR_HEIGHT = 56
}

@SuppressWarnings("CyclomaticComplexMethod")
@Composable
internal fun SearchScreen(
    resetScroll: Boolean,
    postScroll: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    onPostClick: (postId: String, postUrl: String) -> Unit,
    onVideoFullScreenIconClick: (videoUrl: String?) -> Unit,
    onImageFullScreenIconClick: (List<String>) -> Unit,
) {
    val context = LocalContext.current
    val lazyListState = rememberLazyListState()
    val uiState by remember { viewModel.searchDataUiState }.collectAsStateWithLifecycle()
    val recentSearches by remember { viewModel.recentSearches }.collectAsStateWithLifecycle()
    val searchedValue by remember { viewModel.searchQuery }.collectAsStateWithLifecycle()
    val searchBarActive by remember { viewModel.searchBarActive }.collectAsStateWithLifecycle()
    var showErrorDialog by remember { mutableStateOf(false) }
    val searchedItemsList by remember(uiState) { mutableStateOf(uiState.searchedData.orEmpty()) }
    val configuration = LocalConfiguration.current
    lazyListState.ScrollHelper(resetScroll = resetScroll, postScroll)
    val currentlyPlayingItem = determineCurrentlyPlayingItem(lazyListState, uiState.searchedData)
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build()
    }

    val searchBarPadding by animateDpAsState(
        targetValue = if (searchedItemsList.isNotEmpty() && !searchBarActive) 16.dp else 0.dp,
        label = "searchBarPadding",
    )

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

    Box(modifier = modifier) {
        if (searchedItemsList.isNotEmpty()) {
            LazyColumn(
                state = lazyListState,
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = WindowInsets.navigationBars.asPaddingValues(),
            ) {
                items(
                    count = searchedItemsList.size,
                    key = { index -> searchedItemsList[index].id },
                    contentType = { REDDIT_POST },
                ) { index ->
                    PostComponent(
                        modifier = Modifier
                            .padding(
                                top = if (index == 0) {
                                    WindowInsets.statusBars.asPaddingValues()
                                        .calculateTopPadding() + SEARCH_BAR_HEIGHT.dp + 8.dp
                                } else {
                                    0.dp
                                },
                            ),
                        redditPostUiModel = searchedItemsList[index],
                        onClick = { id, url ->
                            viewModel.saveRecentSearch(searchedValue)
                            onPostClick(id, url)
                        },
                        onSaveIconClick = {
                            viewModel.onSaveIconClick(searchedItemsList[index])
                        },
                        onShareIconClick = { postUrl -> shareRedditPost(postUrl, context) },
                        onImageFullScreenIconClick = onImageFullScreenIconClick,
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

        if (searchedItemsList.isEmpty() && !uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        modifier = Modifier
                            .padding(top = 96.dp)
                            .width(220.dp),
                        painter = painterResource(R.drawable.search),
                        contentDescription = null,
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = lazyListState.isScrollingUp(),
            enter = slideInFromTop(),
            exit = slideOutToTop2(),
        ) {
            BRSearchBar(
                modifier = Modifier
                    .padding(horizontal = searchBarPadding),
                query = searchedValue,
                active = searchBarActive,
                configuration = configuration,
                onActiveChange = {
                    viewModel.updateSearchBarActive(it)
                },
                onQueryChange = {
                    viewModel.updateSearchQuery(it)
                },
                onSearch = {
                    viewModel.saveRecentSearch(searchedValue)
                },
                onBack = {
                    viewModel.onBackClick(searchedValue)
                },
            ) {
                SearchBarContentView(
                    uiState = uiState,
                    recentSearches = recentSearches,
                    searchedValue = searchedValue,
                    onSeeAllResultsClick = {
                        viewModel.saveRecentSearch(searchedValue)
                        viewModel.updateSearchBarActive(false)
                    },
                    onViewAllPostsClick = {
                        viewModel.saveRecentSearch(searchedValue)
                        viewModel.updateSearchBarActive(false)
                    },
                    onRecentItemClick = { recentSearch ->
                        viewModel.updateSearchQuery(recentSearch.value)
                    },
                    onRecentItemDeleteClick = { recentSearch ->
                        viewModel.onDeleteItemClick(recentSearch)
                    },
                    onQuickSearchPostClick = { id, url ->
                        viewModel.saveRecentSearch(searchedValue)
                        onPostClick(id, url)
                    },
                    onClearAllRecentSearchesClick = {
                        viewModel.clearAllRecentSearches()
                    },
                )
            }
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
private fun SearchBarContentView(
    uiState: SearchDataUiModel,
    recentSearches: List<RecentSearch>,
    searchedValue: String,
    onSeeAllResultsClick: () -> Unit,
    onViewAllPostsClick: () -> Unit,
    onClearAllRecentSearchesClick: () -> Unit,
    onRecentItemClick: (RecentSearch) -> Unit,
    onRecentItemDeleteClick: (RecentSearch) -> Unit,
    onQuickSearchPostClick: (id: String, url: String) -> Unit,
) {
    val searchedData by remember(uiState) { mutableStateOf(uiState.searchedData.orEmpty()) }

    AnimatedVisibility(
        shouldShowRecentSearches(searchedValue, uiState, recentSearches),
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        RecentSearchesComponent(
            modifier = Modifier,
            recentSearches = recentSearches,
            onItemClick = onRecentItemClick,
            onDeleteItemClick = onRecentItemDeleteClick,
            onClearAllClick = onClearAllRecentSearchesClick,
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
