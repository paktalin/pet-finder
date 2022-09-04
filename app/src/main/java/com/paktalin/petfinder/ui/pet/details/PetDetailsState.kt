package com.paktalin.petfinder.ui.pet.details

import com.paktalin.petfinder.model.Gender

data class PetDetailsState(
    val name: String = "",
    val description: String? = null,
    val pictureUrl: String? = null,
    val phoneNumber: String? = null,
    val gender: Gender = Gender.UNKNOWN,
)
