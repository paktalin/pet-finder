package com.paktalin.catfacts.mapping

import com.paktalin.catfacts.data.local.entity.FactEntity
import com.paktalin.catfacts.data.remote.dto.FactsResponseDto

fun FactsResponseDto.toEntities(): List<FactEntity> {
    return data.map {
        FactEntity(
            id = it.fact.hashCode().toString(),
            fact = it.fact
        )
    }
}
