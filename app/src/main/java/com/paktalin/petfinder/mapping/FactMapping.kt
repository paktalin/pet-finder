package com.paktalin.petfinder.mapping

import com.paktalin.petfinder.data.local.entity.FactEntity
import com.paktalin.petfinder.data.remote.dto.FactsResponseDto
import com.paktalin.petfinder.model.Fact

fun FactsResponseDto.toEntities(): List<FactEntity> {
    return data.map {
        FactEntity(
            id = it.fact.hashCode().toString(),
            fact = it.fact
        )
    }
}

fun List<FactEntity>.toModels() = map { it.toModel() }

fun FactEntity.toModel() = Fact(id = id, text = fact)
