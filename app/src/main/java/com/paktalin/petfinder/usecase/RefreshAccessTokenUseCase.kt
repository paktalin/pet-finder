package com.paktalin.petfinder.usecase

import com.paktalin.petfinder.data.local.DevicePreferences
import com.paktalin.petfinder.data.remote.PetFinderService
import dagger.Reusable
import timber.log.Timber
import javax.inject.Inject

@Reusable
class RefreshAccessTokenUseCase @Inject constructor(
    private val devicePreferences: DevicePreferences,
//    private val petFinderService: PetFinderService
) {

    suspend operator fun invoke() {
        Timber.e("refresh")
        /*val response = petFinderService.refreshAccessToken()
        devicePreferences.setAccessToken(response.access_token)*/
    }
}
