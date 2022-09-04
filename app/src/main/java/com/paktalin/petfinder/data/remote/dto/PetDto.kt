package com.paktalin.petfinder.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PetDto(
    val id: Long,
    val name: String,
    val description: String?,
    val primary_photo_cropped: PhotoDto?,
    val type: String,
    val gender: GenderDto,
    val contact: ContactDto,
)

@Serializable
data class PhotoDto(val medium: String)

@Serializable
data class ContactDto(val phone: String?)

@Serializable
enum class GenderDto {
    @SerialName("Female")
    FEMALE,

    @SerialName("Male")
    MALE,

    @SerialName("Unknown")
    UNKNOWN
}
