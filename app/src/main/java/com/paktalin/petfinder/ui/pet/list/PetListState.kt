package com.paktalin.petfinder.ui.pet.list

import com.paktalin.petfinder.model.Pet

data class PetListState(
    val pets: List<Pet> = emptyList(),
    val isLoadingVisible: Boolean = false,
    val toolbarText: String? = null,
)
