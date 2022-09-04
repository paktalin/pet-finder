package com.paktalin.petfinder.model

data class Pet(
    val id: Long,
    val name: String,
    val description: String?,
    val smallPictureUrl: String?,
    val type: String
)
