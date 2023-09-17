package com.bestway.broncoforreddit.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestway.broncoforreddit.data.repositories.homerepository.HomeRepository
import com.bestway.broncoforreddit.data.repositories.models.ListingsData
import com.bestway.broncoforreddit.data.repositories.models.ListingsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    private val _trendingPosts: MutableStateFlow<ListingsData> = MutableStateFlow(
        ListingsData()
    )
    var trendingPosts: StateFlow<ListingsData> = _trendingPosts.asStateFlow()


    fun getTrendingPosts() {
        viewModelScope.launch {
          repository.getTendingPosts().collectLatest { listingsResponse ->
                listingsResponse.data?.let { responseListingsData ->
                    _trendingPosts.update {
                        it.copy(
                            after = responseListingsData.after,
                            dist = responseListingsData.dist,
                            modhash = responseListingsData.modhash,
                            geoFilter = responseListingsData.geoFilter,
                            children = responseListingsData.children,
                            before = responseListingsData.before
                        )
                    }
                }
            }
        }
    }
}