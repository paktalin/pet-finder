package com.paktalin.petfinder.usecase

import com.paktalin.petfinder.model.Pet
import dagger.Reusable
import javax.inject.Inject

@Reusable
class GetPetTypesFomPetsUseCase @Inject constructor(){

    operator fun invoke(pets: List<Pet>) : List<String> {
        return pets.map { it.type }.toSet().toList()
    }
}
