package com.paktalin.petfinder.ui.pet.list

sealed interface PetListAction {
    data class ShowError(val error: Exception) : PetListAction
}
