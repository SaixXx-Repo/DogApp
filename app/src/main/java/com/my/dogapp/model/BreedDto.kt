package com.my.dogapp.model

import android.os.Parcelable
import com.my.dogapp.utils.adapter.RecyclerListItem
import com.my.dogapp.utils.extension.capitalize
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BreedDto(
    val id: Long? = null,
    val breedName: String,
    val subBreedName: String?
) : RecyclerListItem, Parcelable {

    fun getFullName(): String = if (subBreedName.isNullOrBlank()) {
        breedName.capitalize()
    } else {
        "${breedName.capitalize()} ${subBreedName.capitalize()}"
    }
}