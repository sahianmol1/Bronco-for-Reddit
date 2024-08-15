package com.anmolsahi.data.mapper

import com.anmolsahi.data.local.RecentSearchEntity
import com.anmolsahi.domain.model.RecentSearch

internal fun RecentSearchEntity.asDomain() = RecentSearch(
    value = name,
)

internal fun RecentSearch.asEntity() = RecentSearchEntity(
    name = value,
)
