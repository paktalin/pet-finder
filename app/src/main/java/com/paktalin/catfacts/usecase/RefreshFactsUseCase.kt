package com.paktalin.catfacts.usecase

import com.paktalin.catfacts.data.local.FactDao
import com.paktalin.catfacts.data.remote.CatFactsService
import com.paktalin.catfacts.mapping.toEntities
import dagger.Reusable
import javax.inject.Inject

@Reusable
class RefreshFactsUseCase @Inject constructor(
    private val catFactsService: CatFactsService,
    private val factDao: FactDao,
) {
    suspend operator fun invoke(): Result {
        return try {
            val response = catFactsService.getFacts()
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
