package com.paktalin.petfinder.data.local

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DevicePreferences @Inject constructor(@ApplicationContext private val context: Context) {

    private val dataStore = createDataStore(context)

    suspend fun getAccessToken(): String? = dataStore.data.first()[ACCESS_TOKEN]

    suspend fun setAccessToken(accessToken: String?) = dataStore.edit {
        if (accessToken == null) it.remove(ACCESS_TOKEN)
        else it[ACCESS_TOKEN] = accessToken
    }
}

private val ACCESS_TOKEN = stringPreferencesKey("access_token")

private const val DEVICE_PREFERENCES_FILE_NAME = "com.paktalin.petfinder.DevicePreferences"

private fun createDataStore(context: Context) = PreferenceDataStoreFactory.create {
    context.preferencesDataStoreFile(DEVICE_PREFERENCES_FILE_NAME)
}
