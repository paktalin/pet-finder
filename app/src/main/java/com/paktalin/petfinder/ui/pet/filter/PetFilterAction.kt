package com.paktalin.petfinder.ui.pet.filter

sealed interface PetFilterAction {
    data class ShowError(val error: Exception) : PetFilterAction
}
