package com.bestway.data.mapper

import com.bestway.data.local.RecentSearchEntity
import com.bestway.domain.model.RecentSearch

fun RecentSearchEntity.asDomain() = RecentSearch(
    id = id,
    value = name,
)

fun RecentSearch.asEntity() = RecentSearchEntity(
    id = id,
    name = value,
)