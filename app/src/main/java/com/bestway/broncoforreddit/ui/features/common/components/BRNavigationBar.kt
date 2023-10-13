package com.bestway.broncoforreddit.ui.features.common.components

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bestway.broncoforreddit.R
import com.bestway.broncoforreddit.navigation.Screens
import com.bestway.broncoforreddit.ui.features.home.model.BottomNavUiModel

@Composable
fun BRNavigationBar(
    modifier: Modifier = Modifier,
    onNavItemClick: (route: String) -> Unit,
    context: Context
) {
    val bottomNavItems by remember {
        mutableStateOf(
            listOf(
                BottomNavUiModel(
                    route = Screens.HomeScreen.route,
                    title = context.getString(R.string.home),
                    selectedIcon = Icons.Filled.Home,
                    unselectedIcon = Icons.Outlined.Home
                ),
                BottomNavUiModel(
                    route = Screens.SearchScreen.route,
                    title = context.getString(R.string.search),
                    selectedIcon = Icons.Filled.Search,
                    unselectedIcon = Icons.Outlined.Search
                ),
                BottomNavUiModel(
                    route = Screens.SubScreen.route,
                    title = context.getString(R.string.subs),
                    selectedIcon = Icons.Filled.List,
                    unselectedIcon = Icons.Outlined.List
                ),
                BottomNavUiModel(
                    route = Screens.AboutUsScreen.route,
                    title = context.getString(R.string.about),
                    selectedIcon = Icons.Filled.Info,
                    unselectedIcon = Icons.Outlined.Info
                ),
            )
        )
    }
    var selectedBottomNavIndex by rememberSaveable { mutableIntStateOf(0) }

    BRNavigationBarView(
        modifier = modifier,
        bottomNavItems = bottomNavItems,
        selectedBottomNavIndex = selectedBottomNavIndex,
        onNavItemClick = { index ->
            onNavItemClick(bottomNavItems[index].route)
            selectedBottomNavIndex = index
        }
    )
}

@Composable
fun BRNavigationBarView(
    modifier: Modifier = Modifier,
    bottomNavItems: List<BottomNavUiModel>,
    selectedBottomNavIndex: Int,
    onNavItemClick: (index: Int) -> Unit
) {

    NavigationBar(
        modifier = modifier
    ) {
        bottomNavItems.forEachIndexed { index, bottomNav ->
            NavigationBarItem(
                selected = selectedBottomNavIndex == index,
                onClick = {
                    onNavItemClick(index)
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