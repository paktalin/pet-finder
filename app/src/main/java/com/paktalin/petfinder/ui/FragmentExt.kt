package com.paktalin.petfinder.ui

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.paktalin.petfinder.R
import timber.log.Timber

fun Fragment.showError(error: Exception) {
    Timber.w(error, "showError")
    showSnackbar(error.message ?: getString(R.string.general_error))
}

fun Fragment.showSnackbar(message: CharSequence) {
    Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
}
