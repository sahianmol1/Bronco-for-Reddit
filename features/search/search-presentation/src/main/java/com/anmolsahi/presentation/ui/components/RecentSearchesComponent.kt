package com.anmolsahi.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anmolsahi.designsystem.theme.BRTheme
import com.anmolsahi.domain.model.RecentSearch
import com.anmolsahi.presentation.ui.components.RecentSearchesComponentConstants.SHOW_CLEAR_ALL_LABEL_REQUIREMENT
import com.anmolsahi.searchpresentation.R

private object RecentSearchesComponentConstants {
    const val SHOW_CLEAR_ALL_LABEL_REQUIREMENT = 7
}

@Composable
internal fun RecentSearchesComponent(
    recentSearches: List<RecentSearch>,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    onItemClick: (RecentSearch) -> Unit,
    onDeleteItemClick: (RecentSearch) -> Unit,
    onClearAllClick: () -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        state = lazyListState,
    ) {
        item(
            key = "recent_searches_title",
            contentType = "recent_searches_title",
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(R.string.recent_searches),
                    fontWeight = FontWeight.Bold,
                )

                AnimatedVisibility(recentSearches.size > SHOW_CLEAR_ALL_LABEL_REQUIREMENT) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clickable { onClearAllClick() },
                        text = stringResource(R.string.clear_all),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline,
                        color = BRTheme.colorScheme.error,
                    )
                }
            }
        }

        items(
            count = recentSearches.size,
            key = { index -> recentSearches[index].value },
            contentType = { "recent_searches_item" },
        ) { index ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(recentSearches[index]) },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier.padding(start = 16.dp),
                    imageVector = Icons.Default.History,
                    contentDescription =
                    stringResource(
                        R.string.search_item,
                        recentSearches[index].value,
                    ),
                )

                Text(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .weight(1f),
                    text = recentSearches[index].value,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                IconButton(
                    modifier = Modifier
                        .padding(end = 8.dp),
                    onClick = { onDeleteItemClick(recentSearches[index]) },
                ) {
                    Icon(
                        modifier = Modifier,
                        imageVector = Icons.Default.Close,
                        contentDescription =
                        stringResource(
                            R.string.remove,
                            recentSearches[index].value,
                        ),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RecentSearchesPreview() {
    RecentSearchesComponent(
        recentSearches =
        listOf(
            RecentSearch("cat"),
            RecentSearch("dog"),
            RecentSearch("bird"),
            RecentSearch("funny"),
        ),
        onItemClick = {},
        onDeleteItemClick = {},
        onClearAllClick = {},
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SingleRecentSearchePreview() {
    RecentSearchesComponent(
        recentSearches =
        listOf(
            RecentSearch("cat"),
            RecentSearch("dog"),
            RecentSearch("bird"),
            RecentSearch("funny"),
            RecentSearch("cute"),
            RecentSearch("r/aww"),
            RecentSearch("hello"),
            RecentSearch("movies"),
        ),
        onItemClick = {},
        onDeleteItemClick = {},
        onClearAllClick = {},
    )
}
