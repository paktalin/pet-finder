package com.paktalin.petfinder.usecase

import com.paktalin.petfinder.data.remote.PetFinderService
import com.paktalin.petfinder.mapping.toModels
import com.paktalin.petfinder.model.PetType
import dagger.Reusable
import javax.inject.Inject

@Reusable
class GetPetTypesUseCase @Inject constructor(private val petFinderService: PetFinderService) {

    suspend operator fun invoke(): List<PetType> {
        return petFinderService.getPetTypes().toModels()
    }
}
