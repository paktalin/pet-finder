package com.paktalin.petfinder.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenResponseDto(val access_token: String)
