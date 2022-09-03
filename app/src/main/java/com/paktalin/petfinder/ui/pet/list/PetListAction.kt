package com.paktalin.petfinder.ui.pet.list

sealed interface PetListAction {
    data class ShowError(val error: Exception) : PetListAction
    data class NavigateToDetails(val id: Long) : PetListAction
    object NavigateToFilters : PetListAction
}
