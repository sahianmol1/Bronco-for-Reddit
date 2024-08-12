package com.anmolsahi.presentation.ui.components

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anmolsahi.domain.model.RecentSearch
import com.anmolsahi.searchpresentation.R

@Composable
fun RecentSearchesComponent(
    recentSearches: List<RecentSearch>,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    onItemClick: (RecentSearch) -> Unit,
    onDeleteItemClick: (RecentSearch) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        state = lazyListState,
    ) {
        item(
            key = "recent_searches_title",
            contentType = "recent_searches_title",
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.recent_searches),
                fontWeight = FontWeight.Bold,
            )
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
    )
}
