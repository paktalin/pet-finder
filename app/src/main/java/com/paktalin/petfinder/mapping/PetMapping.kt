package com.paktalin.petfinder.mapping

import com.paktalin.petfinder.data.local.entity.PetEntity
import com.paktalin.petfinder.data.remote.dto.PetsResponseDto
import com.paktalin.petfinder.model.Pet

fun PetsResponseDto.toEntities(): List<PetEntity> {
    return animals.map {
        PetEntity(
            id = it.id,
            name = it.name,
            description = it.description,
            primaryPhotoSmall = it.primary_photo_cropped?.small
        )
    }
}

fun List<PetEntity>.toModels() = map { it.toModel() }

fun PetEntity.toModel(): Pet {
    return Pet(
        id = id,
        name = name,
        description = description,
        smallPictureUrl = primaryPhotoSmall
    )
}
