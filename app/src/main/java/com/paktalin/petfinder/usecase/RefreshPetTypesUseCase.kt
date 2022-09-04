package com.paktalin.petfinder.usecase

import com.paktalin.petfinder.data.local.PetTypeDao
import com.paktalin.petfinder.data.remote.PetFinderService
import com.paktalin.petfinder.mapping.toEntities
import dagger.Reusable
import javax.inject.Inject

@Reusable
class RefreshPetTypesUseCase @Inject constructor(
    private val petFinderService: PetFinderService,
    private val petTypeDao: PetTypeDao,
) {

    suspend operator fun invoke(): Result {
        return try {
            val response = petFinderService.getPetTypes()
            petTypeDao.replacePetTypes(response.toEntities())
            Result.Success
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    sealed class Result {
        object Success : Result()
        data class Failure(val error: Exception) : Result()
    }
}
