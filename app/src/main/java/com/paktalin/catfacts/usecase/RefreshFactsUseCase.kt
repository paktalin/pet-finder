package com.paktalin.catfacts.usecase

import com.paktalin.catfacts.data.remote.CatFactsService
import dagger.Reusable
import timber.log.Timber
import javax.inject.Inject

@Reusable
class RefreshFactsUseCase @Inject constructor(private val catFactsService: CatFactsService, ) {
    suspend operator fun invoke() {
        try {
            val response = catFactsService.getFacts()
            // TODO save facts
            Timber.e("response: $response")
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
