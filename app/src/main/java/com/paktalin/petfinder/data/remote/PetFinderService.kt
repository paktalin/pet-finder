package com.paktalin.petfinder.data.remote

import com.paktalin.petfinder.data.remote.dto.FactsResponseDto
import retrofit2.http.GET

interface PetFinderService {
    @GET("facts")
    suspend fun getFacts(): FactsResponseDto
}
