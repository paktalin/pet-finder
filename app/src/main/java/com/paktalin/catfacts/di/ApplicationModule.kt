package com.paktalin.catfacts.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.paktalin.catfacts.BuildConfig
import com.paktalin.catfacts.data.local.ApplicationDb
import com.paktalin.catfacts.data.remote.CatFactsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            )
            .build()
    }

    @Provides
    @Singleton
    fun retrofit(okHttpClient: OkHttpClient, json: Json): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun catFactsService(retrofit: Retrofit): CatFactsService = retrofit.create()

    @Provides
    @Singleton
    fun json(): Json {
        return Json {
            encodeDefaults = true
            ignoreUnknownKeys = true
        }
    }

    @Singleton
    @Provides
    fun applicationDb(@ApplicationContext context: Context): ApplicationDb {
        return Room
            .databaseBuilder(context, ApplicationDb::class.java, "application")
            .build()
    }
}
