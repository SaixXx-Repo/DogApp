package com.my.dogapp.model

import com.my.dogapp.utils.adapter.RecyclerListItem
import com.my.dogapp.utils.extension.capitalize

data class BreedPostDto(
    val breedName: String,
    val subBreedName: String?,
    val url: String,
    val isFavourite: Boolean
) : RecyclerListItem {

    fun getFullName(): String = if (subBreedName.isNullOrBlank()) {
        breedName.capitalize()
    } else {
        "${breedName.capitalize()} ${subBreedName.capitalize()}"
    }

    override fun isSameItemAs(otherItem: RecyclerListItem): Boolean {
        return otherItem is BreedPostDto && otherItem.url == url
    }
}