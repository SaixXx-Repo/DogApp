package com.my.dogapp.model.extension

import com.my.dogapp.data.persistence.entity.BreedPostEntity
import com.my.dogapp.model.BreedPostDto

fun BreedPostDto.asBreedPostEntity(): BreedPostEntity {
    return BreedPostEntity(
        breedName = breedName,
        subBreedName = subBreedName,
        url = url,
        isFavourite = isFavourite
    )
}