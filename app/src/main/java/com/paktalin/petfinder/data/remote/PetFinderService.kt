package com.paktalin.petfinder.data.remote

import com.paktalin.petfinder.data.remote.dto.AccessTokenResponseDto
import com.paktalin.petfinder.data.remote.dto.PetsResponseDto
import retrofit2.http.GET

interface PetFinderService {
    @GET("animals")
    suspend fun getPets(): PetsResponseDto

    @GET("oauth2/token")
    suspend fun refreshAccessToken(): AccessTokenResponseDto
}
