package com.paktalin.petfinder.usecase

import com.paktalin.petfinder.data.local.FactDao
import com.paktalin.petfinder.data.remote.PetFinderService
import com.paktalin.petfinder.mapping.toEntities
import dagger.Reusable
import javax.inject.Inject

@Reusable
class RefreshFactsUseCase @Inject constructor(
    private val petFinderService: PetFinderService,
    private val factDao: FactDao,
) {
    suspend operator fun invoke(): Result {
        return try {
            val response = petFinderService.getFacts()
            factDao.replaceFacts(response.toEntities())
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
