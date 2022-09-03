package com.paktalin.catfacts.usecase

import com.paktalin.catfacts.data.local.FactDao
import com.paktalin.catfacts.mapping.toModels
import com.paktalin.catfacts.model.Fact
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
