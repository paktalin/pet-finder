package com.paktalin.petfinder.usecase

import com.paktalin.petfinder.data.local.FactDao
import com.paktalin.petfinder.mapping.toModels
import com.paktalin.petfinder.model.Fact
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@Reusable
class ObserveFactsUseCase @Inject constructor(private val factDao: FactDao) {

    operator fun invoke(): Flow<List<Fact>> {
        return factDao.observeFacts().map { it.toModels() }
    }
}
