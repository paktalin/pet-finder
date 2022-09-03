package com.paktalin.catfacts.mapping

import com.paktalin.catfacts.data.local.entity.FactEntity
import com.paktalin.catfacts.data.remote.dto.FactsResponseDto
import com.paktalin.catfacts.model.Fact

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
