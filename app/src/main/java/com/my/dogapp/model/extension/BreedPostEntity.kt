package com.my.dogapp.model.extension

import com.my.dogapp.data.persistence.entity.BreedPostEntity
import com.my.dogapp.model.BreedPostDto

fun BreedPostEntity.asBreedPostDto(): BreedPostDto {
    return BreedPostDto(
        breedName = breedName,
        subBreedName = subBreedName,
        url = url,
        isFavourite = isFavourite
    )
}