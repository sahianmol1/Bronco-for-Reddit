package com.anmolsahi.postdetailsdata.utils

import com.anmolsahi.data.model.remote.ListingsResponse

internal fun List<ListingsResponse>.postContent() = first()

internal fun List<ListingsResponse>.comments() = get(1)
