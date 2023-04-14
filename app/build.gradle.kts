plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization") version libs.versions.kotlin.get()
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.paktalin.petfinder"
        minSdk = 24
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "BASE_URL", "\"https://api.petfinder.com/v2/\"")
        buildConfigField("String", "CLIENT_ID", "\"xLfSvSKKAGy7CXj5OpzDAIXUUL8ffzN0nfj3lBQThpYVhINIfP\"")
        buildConfigField("String", "CLIENT_SECRET", "\"2YERpGf0zZNYMfQy1g8GumFB1JCHO6WFy7L5z9Lm\"")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = listOf("-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi")
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.core.coreKtx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.fragmentKtx)
    implementation(libs.androidx.navigation.fragmentKtx)
    implementation(libs.androidx.roomKtx)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.datastore.preferences)

    implementation(libs.jakewharton.timber)
    kapt(libs.androidx.hilt.compiler)

    kapt(libs.google.hilt.compiler)
    implementation(libs.google.android.material)
    implementation(libs.google.hilt.android)

    implementation(libs.squareup.okhttp)
    implementation(libs.squareup.okhttp.loggingInterceptor)
    implementation(libs.squareup.retrofit)
    implementation(libs.jakewharton.retrofitKotlinxConverter)
    implementation(libs.coil)

}
