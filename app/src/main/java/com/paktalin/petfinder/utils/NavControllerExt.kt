package com.paktalin.petfinder.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber

fun Fragment.navigate(directions: NavDirections) = findNavController().tryNavigate(directions)

fun Fragment.navigateUp(): Boolean = findNavController().tryNavigateUp()

fun NavController.tryNavigate(directions: NavDirections) {
    try {
        navigate(directions)
    } catch (e: Exception) {
        Timber.w(e, "navigate $directions")
    }
}

fun NavController.tryNavigateUp(): Boolean {
    return try {
        navigateUp()
    } catch (e: Exception) {
        Timber.w(e, "navigateUp")
        false
    }
}

fun <T> Fragment.getDestinationResult(
    key: String,
    @IdRes destinationId: Int
): Flow<T> = findNavController().getDestinationResult(key, destinationId)

fun <T> Fragment.setDestinationResult(
    key: String,
    value: T,
    @IdRes destinationId: Int? = null
) = findNavController().setDestinationResult(key, value, destinationId)


fun <T> NavController.getDestinationResult(
    key: String,
    @IdRes destinationId: Int
): Flow<T> = callbackFlow {
    val backStackEntry = getBackStackEntry(destinationId)
    val observer = LifecycleEventObserver { _: LifecycleOwner, event: Lifecycle.Event ->
        if (event == Lifecycle.Event.ON_RESUME) {
            backStackEntry.savedStateHandle.get<T>(key)?.let { result ->
                trySend(result)
                backStackEntry.savedStateHandle.remove<T>(key)
            }
        }
    }
    backStackEntry.lifecycle.addObserver(observer)
    awaitClose { backStackEntry.lifecycle.removeObserver(observer) }
}

fun <T> NavController.setDestinationResult(
    key: String,
    value: T,
    @IdRes destinationId: Int? = null
) {
    val entry = if (destinationId != null) {
        getBackStackEntry(destinationId)
    } else {
        previousBackStackEntry
    }
    entry?.savedStateHandle?.set(key, value)
}
