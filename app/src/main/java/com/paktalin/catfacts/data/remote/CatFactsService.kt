package com.paktalin.catfacts.data.remote

import com.paktalin.catfacts.data.remote.dto.FactsResponseDto
import retrofit2.http.GET

interface CatFactsService {
    @GET("facts")
    suspend fun getFacts(): FactsResponseDto
}
