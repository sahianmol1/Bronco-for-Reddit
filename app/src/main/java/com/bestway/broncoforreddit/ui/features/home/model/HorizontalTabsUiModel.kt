package com.bestway.broncoforreddit.ui.features.home.model

data class HorizontalTabsUiModel(
    val id: Int,
    val tabName: String
)

sealed class HomeScreenTopTabs(val id: Int, val name: String) {
    class Trending(val tabId: Int = 0, val tabName: String = "Trending"): HomeScreenTopTabs(id = tabId, name = tabName)
    class New(val tabId: Int = 0, val tabName: String = "New"): HomeScreenTopTabs(id = tabId, name = tabName)
    class Top(val tabId: Int = 0, val tabName: String = "Top"): HomeScreenTopTabs(id = tabId, name = tabName)
    class Best(val tabId: Int = 0, val tabName: String = "Best"): HomeScreenTopTabs(id = tabId, name = tabName)
    class Rising(val tabId: Int = 0, val tabName: String = "Rising"): HomeScreenTopTabs(id = tabId, name = tabName)
    class Controversial(val tabId: Int = 0, val tabName: String = "Controversial") : HomeScreenTopTabs(id = tabId, name = tabName)

}