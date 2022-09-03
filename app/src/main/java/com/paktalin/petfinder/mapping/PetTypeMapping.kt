package com.paktalin.petfinder.mapping

import com.paktalin.petfinder.data.remote.dto.PetTypeDto
import com.paktalin.petfinder.data.remote.dto.PetTypesResponseDto
import com.paktalin.petfinder.model.PetType

fun PetTypesResponseDto.toModels() = types.map { it.toModel() }

fun PetTypeDto.toModel() = PetType(name = name)
