package com.paktalin.petfinder.ui.pet.list

sealed interface PetListEvent {
    object FilterClick : PetListEvent
    data class ItemClick(val id: Long) : PetListEvent
}
