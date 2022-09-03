package com.paktalin.petfinder.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PetTypesResponseDto(val types: List<PetTypeDto>)
