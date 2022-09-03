package com.paktalin.petfinder.utils

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import timber.log.Timber

fun Fragment.navigateUp(): Boolean = findNavController().tryNavigateUp()

fun NavController.tryNavigateUp(): Boolean {
    return try {
        navigateUp()
    } catch (e: Exception) {
        Timber.w(e, "navigateUp")
        false
    }
}
