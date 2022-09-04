package com.paktalin.petfinder.ui.pet.details

sealed interface PetDetailsAction {
    data class Call(val phoneNumber: String) : PetDetailsAction
}
