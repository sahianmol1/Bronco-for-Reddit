package com.bestway.broncoforreddit.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bestway.broncoforreddit.data.models.ListingsData
import com.bestway.broncoforreddit.data.repositories.homerepository.HomeRepository
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

    private val _newPosts: MutableStateFlow<ListingsData> = MutableStateFlow(
        ListingsData()
    )
    var newPosts: StateFlow<ListingsData> = _newPosts.asStateFlow()

    private val _topPosts: MutableStateFlow<ListingsData> = MutableStateFlow(
        ListingsData()
    )
    var topPosts: StateFlow<ListingsData> = _topPosts.asStateFlow()

    private val _bestPosts: MutableStateFlow<ListingsData> = MutableStateFlow(
        ListingsData()
    )
    var bestPosts: StateFlow<ListingsData> = _bestPosts.asStateFlow()

    private val _risingPosts: MutableStateFlow<ListingsData> = MutableStateFlow(
        ListingsData()
    )
    var risingPosts: StateFlow<ListingsData> = _risingPosts.asStateFlow()

    private val _controversialPosts: MutableStateFlow<ListingsData> = MutableStateFlow(
        ListingsData()
    )
    var controversialPosts: StateFlow<ListingsData> = _controversialPosts.asStateFlow()


    fun getTrendingPosts() {
        viewModelScope.launch {
          repository.getTrendingPosts().collectLatest { listingsResponse ->
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

    fun getNewPosts() {
        viewModelScope.launch {
            repository.getNewPosts().collectLatest { listingsResponse ->
                listingsResponse.data?.let { responseListingsData ->
                    _newPosts.update {
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

    fun getTopPosts() {
        viewModelScope.launch {
            repository.getTopPosts().collectLatest { listingsResponse ->
                listingsResponse.data?.let { responseListingsData ->
                    _topPosts.update {
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


    fun getBestPosts() {
        viewModelScope.launch {
            repository.getBestPosts().collectLatest { listingsResponse ->
                listingsResponse.data?.let { responseListingsData ->
                    _bestPosts.update {
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


    fun getRisingsPosts() {
        viewModelScope.launch {
            repository.getRisingPosts().collectLatest { listingsResponse ->
                listingsResponse.data?.let { responseListingsData ->
                    _risingPosts.update {
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

    fun getControversialPosts() {
        viewModelScope.launch {
            repository.getControversialPosts().collectLatest { listingsResponse ->
                listingsResponse.data?.let { responseListingsData ->
                    _controversialPosts.update {
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