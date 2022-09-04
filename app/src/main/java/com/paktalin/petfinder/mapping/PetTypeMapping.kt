package com.paktalin.petfinder.mapping

import com.paktalin.petfinder.data.local.entity.PetTypeEntity
import com.paktalin.petfinder.data.remote.dto.PetTypeDto
import com.paktalin.petfinder.data.remote.dto.PetTypesResponseDto
import com.paktalin.petfinder.model.PetType


fun PetTypesResponseDto.toEntities() = types.map { it.toEntity() }

fun PetTypeDto.toEntity() = PetTypeEntity(name = name)

fun List<PetTypeEntity>.toModels() = map { it.toModel() }

fun PetTypeEntity.toModel() = PetType(name = name)
