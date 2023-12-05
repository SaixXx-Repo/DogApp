package com.my.dogapp.model.extension

import com.my.dogapp.data.persistence.entity.BreedEntity
import com.my.dogapp.model.BreedDto

fun BreedEntity.asBreedDto(): BreedDto {
    return BreedDto(
        id = id,
        breedName = breedName,
        subBreedName = subBreedName,
    )
}