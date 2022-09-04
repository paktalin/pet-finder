package com.paktalin.petfinder.data.remote

import com.paktalin.petfinder.data.remote.dto.PetsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PetFinderService {
    @GET("animals")
    suspend fun getPets(@Query("before") before: String = "2022-09-01T19:13:01+00:00"): PetsResponseDto
}
