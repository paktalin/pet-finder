package com.paktalin.petfinder.data.remote

import com.paktalin.petfinder.data.remote.dto.PetsResponseDto
import retrofit2.http.GET

interface PetFinderService {
    @GET("animals")
    suspend fun getPets(): PetsResponseDto
}