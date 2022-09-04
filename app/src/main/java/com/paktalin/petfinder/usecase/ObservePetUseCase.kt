package com.paktalin.petfinder.usecase

import com.paktalin.petfinder.data.local.PetDao
import com.paktalin.petfinder.mapping.toModel
import com.paktalin.petfinder.model.Pet
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@Reusable
class ObservePetUseCase @Inject constructor(private val petDao: PetDao) {

    operator fun invoke(id: Long): Flow<Pet?> {
        return petDao.observePet(id).map { it?.toModel() }
    }
}
