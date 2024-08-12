package com.anmolsahi.data.mapper

import com.anmolsahi.data.local.RecentSearchEntity
import com.anmolsahi.domain.model.RecentSearch

fun RecentSearchEntity.asDomain() = RecentSearch(
    value = name,
)

fun RecentSearch.asEntity() = RecentSearchEntity(
    name = value,
)
