package com.bestway.broncoforreddit.ui.widgets

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bestway.broncoforreddit.R
import com.bestway.broncoforreddit.ui.model.BottomNav

@Composable
fun BRNavigationBar(
    modifier: Modifier = Modifier,
    context: Context
) {
    val bottomNavItems by remember {
        mutableStateOf(
            listOf(
                BottomNav(
                    title = context.getString(R.string.home),
                    selectedIcon = Icons.Filled.Home,
                    unselectedIcon = Icons.Outlined.Home
                ),
                BottomNav(
                    title = context.getString(R.string.search),
                    selectedIcon = Icons.Filled.Search,
                    unselectedIcon = Icons.Outlined.Search
                ),
                BottomNav(
                    title = context.getString(R.string.create),
                    selectedIcon = Icons.Filled.Add,
                    unselectedIcon = Icons.Outlined.Add
                ),
                BottomNav(
                    title = context.getString(R.string.subs),
                    selectedIcon = Icons.Filled.List,
                    unselectedIcon = Icons.Outlined.List
                ),
                BottomNav(
                    title = context.getString(R.string.about),
                    selectedIcon = Icons.Filled.Info,
                    unselectedIcon = Icons.Outlined.Info
                ),
            )
        )
    }
    var selectedBottomNavIndex by rememberSaveable { mutableStateOf(0) }

    NavigationBar(
        modifier = modifier
    ) {
        bottomNavItems.forEachIndexed { index, bottomNav ->
            NavigationBarItem(
                selected = selectedBottomNavIndex == index,
                onClick = {
                    selectedBottomNavIndex = index
                },
                icon = {
                    Icon(
                        imageVector = if (selectedBottomNavIndex == index) {
                            bottomNav.selectedIcon
                        } else {
                            bottomNav.unselectedIcon
                        },
                        contentDescription = stringResource(
                            R.string.bottom_bar_content_description,
                            bottomNav.title
                        )
                    )

                },
                label = {
                    Text(text = bottomNav.title)
                }
            )
        }
    }
}