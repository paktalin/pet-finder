package com.paktalin.petfinder.data.remote

import com.paktalin.petfinder.data.remote.dto.PetsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PetFinderService {
    @GET("animals")
    suspend fun getPets(@Query("before") before: String = "2023-04-14T00:00:01+00:00"): PetsResponseDto
}
