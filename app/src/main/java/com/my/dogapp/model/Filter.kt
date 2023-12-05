package com.my.dogapp.model

import com.my.dogapp.utils.adapter.RecyclerListItem

data class Filter(
    val name: String,
    val isApplied: Boolean
) : RecyclerListItem