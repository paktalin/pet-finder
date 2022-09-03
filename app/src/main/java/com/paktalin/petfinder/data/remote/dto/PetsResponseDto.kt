package com.paktalin.petfinder.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PetsResponseDto(
    val animals: List<PetDto>,
)
