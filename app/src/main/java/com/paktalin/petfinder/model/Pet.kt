package com.paktalin.petfinder.model

data class Pet(
    val id: Long,
    val name: String,
    val description: String?,
    val pictureUrl: String?,
    val type: String,
    val gender: Gender,
    val phoneNumber: String?
)

enum class Gender {
    FEMALE, MALE, UNKNOWN
}

