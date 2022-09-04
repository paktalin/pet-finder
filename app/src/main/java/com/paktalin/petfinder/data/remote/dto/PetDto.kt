package com.paktalin.petfinder.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PetDto(
    val id: Long,
    val name: String,
    val description: String?,
    val primary_photo_cropped: PhotoDto?,
    val type: String,
)

@Serializable
data class PhotoDto(val small: String)
