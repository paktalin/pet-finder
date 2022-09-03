package com.paktalin.catfacts.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class FactsResponseDto(
    val total: Int
)
