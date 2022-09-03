package com.paktalin.petfinder.data.remote

import com.paktalin.petfinder.data.local.DevicePreferences
import com.paktalin.petfinder.usecase.RefreshAccessTokenUseCase
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.closeQuietly
import timber.log.Timber
import javax.inject.Inject

class PetFinderInterceptor @Inject constructor(
    private val refreshAccessTokenUseCase: RefreshAccessTokenUseCase,
    private val devicePreferences: DevicePreferences,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .apply {
                runBlocking {
                    addHeader("Authorization", "Bearer ${devicePreferences.getAccessToken()}")
                }
            }
            .build()
        Timber.e("request")

        val response = chain.proceed(request)
        Timber.e("response: $response")
        if (response.code == 401) {
            runBlocking { refreshAccessTokenUseCase() }
            response.closeQuietly()
        }

        return response
    }
}
