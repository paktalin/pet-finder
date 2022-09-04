package com.paktalin.petfinder.usecase

import com.paktalin.petfinder.data.local.PetTypeDao
import com.paktalin.petfinder.mapping.toModels
import com.paktalin.petfinder.model.PetType
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

@Reusable
class ObservePetTypesUseCase @Inject constructor(private val petTypeDao: PetTypeDao) {

    operator fun invoke(): Flow<List<PetType>> {
        return petTypeDao.observePetTypes().map { it.toModels() }
    }
}
