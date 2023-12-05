package com.my.dogapp.model.extension

import com.my.dogapp.data.persistence.entity.BreedEntity
import com.my.dogapp.model.BreedDto

fun BreedDto.asBreedEntity(): BreedEntity {
    return BreedEntity(
        breedName = breedName,
        subBreedName = subBreedName,
    )
}