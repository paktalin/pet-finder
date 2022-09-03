buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath(libs.android.tools.gradle)
        classpath(kotlin("gradle-plugin", version = libs.versions.kotlin.get()))
        classpath(libs.androidx.navigation.safeArgs.gradle)
        classpath(libs.google.hilt.gradle)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
