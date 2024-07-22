package com.anmolsahi.data.mapper

import com.anmolsahi.data.local.RecentSearchEntity
import com.anmolsahi.domain.model.RecentSearch

fun RecentSearchEntity.asDomain() = RecentSearch(
    id = id,
    value = name,
)

fun RecentSearch.asEntity() = RecentSearchEntity(
    id = id,
    name = value,
)
