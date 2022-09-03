package com.paktalin.catfacts.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

internal class ViewLifecycle<T>(
    private val fragment: Fragment,
    private val onCreate: (View) -> T,
    onDestroy: ((T) -> Unit)?,
    private val isLazy: Boolean,
) : ReadOnlyProperty<Fragment, T> {

    private var value: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            val viewLifecycleOwnerLiveDataObserver =
                Observer<LifecycleOwner?> {
                    val viewLifecycleOwner = it ?: return@Observer

                    viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onCreate(owner: LifecycleOwner) {
                            super.onCreate(owner)
                            if (!isLazy) {
                                value = onCreate(fragment.requireView())
                            }
                        }

                        override fun onDestroy(owner: LifecycleOwner) {
                            value?.let { value -> onDestroy?.invoke(value) }
                            value = null
                        }
                    })
                }

            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observeForever(
                    viewLifecycleOwnerLiveDataObserver
                )
            }

            override fun onDestroy(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.removeObserver(
                    viewLifecycleOwnerLiveDataObserver
                )
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val value = value
        if (value != null) {
            return value
        }

        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            error("Fragments view lifecycle is destroyed.")
        }

        return onCreate(thisRef.requireView()).also { this.value = it }
    }
}

fun <T> Fragment.viewLifecycle(factory: (View) -> T): ReadOnlyProperty<Fragment, T> =
    viewLifecycle(factory, null, true)

fun <T> Fragment.viewLifecycle(
    onCreate: (View) -> T,
    onDestroy: ((T) -> Unit)? = null,
    isLazy: Boolean = true,
): ReadOnlyProperty<Fragment, T> {
    return ViewLifecycle(this, onCreate, onDestroy, isLazy)
}
