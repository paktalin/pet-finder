package com.paktalin.petfinder.mapping

import androidx.core.text.HtmlCompat
import com.paktalin.petfinder.data.local.entity.GenderEntity
import com.paktalin.petfinder.data.local.entity.PetEntity
import com.paktalin.petfinder.data.remote.dto.GenderDto
import com.paktalin.petfinder.data.remote.dto.PetsResponseDto
import com.paktalin.petfinder.model.Gender
import com.paktalin.petfinder.model.Pet

fun PetsResponseDto.toEntities(): List<PetEntity> {
    return animals.map {
        PetEntity(
            id = it.id,
            name = it.name.stripHtml(),
            description = it.description?.stripHtml(),
            pictureUrl = it.primary_photo_cropped?.medium,
            type = it.type,
            gender = it.gender.toEntity(),
            phoneNumber = it.contact.phone,
        )
    }
}

fun List<PetEntity>.toModels() = map { it.toModel() }

fun PetEntity.toModel(): Pet {
    return Pet(
        id = id,
        name = name,
        description = description,
        pictureUrl = pictureUrl,
        type = type,
        gender = gender.toModel(),
        phoneNumber = phoneNumber,
    )
}

fun GenderDto.toEntity(): GenderEntity {
    return when (this) {
        GenderDto.FEMALE -> GenderEntity.FEMALE
        GenderDto.MALE -> GenderEntity.MALE
        GenderDto.UNKNOWN -> GenderEntity.UNKNOWN
    }
}

fun GenderEntity.toModel() = when (this) {
    GenderEntity.FEMALE -> Gender.FEMALE
    GenderEntity.MALE -> Gender.MALE
    GenderEntity.UNKNOWN -> Gender.UNKNOWN
}

private fun String.stripHtml(): String {
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
}