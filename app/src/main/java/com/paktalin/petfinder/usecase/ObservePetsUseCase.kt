package com.paktalin.petfinder.usecase

import com.paktalin.petfinder.data.local.PetDao
import com.paktalin.petfinder.mapping.toModels
import com.paktalin.petfinder.model.Pet
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@Reusable
class ObservePetsUseCase @Inject constructor(private val petDao: PetDao) {

    operator fun invoke(): Flow<List<Pet>> {
        return petDao.observePets().map { it.toModels() }
    }
}
