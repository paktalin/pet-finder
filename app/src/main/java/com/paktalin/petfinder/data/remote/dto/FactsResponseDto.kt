package com.paktalin.petfinder.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class FactsResponseDto(
    val total: Int,
    val data: List<FactDto>,
)

@Serializable
data class FactDto(
    val fact: String
)
