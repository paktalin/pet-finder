package com.paktalin.petfinder.data.remote

import com.paktalin.petfinder.BuildConfig
import com.paktalin.petfinder.data.local.DevicePreferences
import com.paktalin.petfinder.data.remote.dto.AccessTokenResponseDto
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class PetFinderInterceptor @Inject constructor(
    private val devicePreferences: DevicePreferences,
    private val json: Json
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { devicePreferences.getAccessToken() }
        val originalRequest = chain.request().newBuilder().apply {
            token?.let(::addTokenHeader)
        }.build()

        val response = chain.proceed(originalRequest)
        if (response.code == 401) {
            val refreshResponse = refreshToken(originalRequest, chain)
            if (refreshResponse.isSuccessful) {
                val refreshedToken = decodeRefreshedToken(refreshResponse)
                runBlocking { devicePreferences.setAccessToken(refreshedToken) }
                val newCall = originalRequest.newBuilder().addTokenHeader(refreshedToken).build()
                return chain.proceed(newCall)
            }
        }
        return response
    }

    private fun refreshToken(request: Request, chain: Interceptor.Chain): Response {
        val body = FormBody.Builder()
            .add("grant_type", "client_credentials")
            .add("client_id", "0NjzgAzMc14Gq1AkvypepRSA8q4V5LRAx8EwVFAeRIRiJNoyYS")
            .add("client_secret", "x7XS0mz8YaA6zfOl03lr8QQJjNfhUAANlwODyTcT")
            .build()

        val refreshTokenRequest = request
            .newBuilder()
            .post(body)
            .url("${BuildConfig.BASE_URL}oauth2/token")
            .build()
        return chain.proceed(refreshTokenRequest)
    }

    private fun decodeRefreshedToken(response: Response): String {
        val body = response.body?.string() ?: return ""
        return json.decodeFromString<AccessTokenResponseDto>(body).access_token
    }
}

private fun Request.Builder.addTokenHeader(token: String): Request.Builder {
    return addHeader("Authorization", "Bearer $token")
}
