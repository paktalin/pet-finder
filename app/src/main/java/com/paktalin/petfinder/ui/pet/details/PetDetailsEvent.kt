package com.paktalin.petfinder.ui.pet.details

sealed interface PetDetailsEvent {
    object CallClick : PetDetailsEvent
}
