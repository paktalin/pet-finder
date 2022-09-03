package com.paktalin.petfinder.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PetsResponseDto(
    val animals: List<PetDto>,
)

@Serializable
data class PetDto(
    val id: Long,
    val name: String
)
