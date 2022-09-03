package com.paktalin.catfacts.ui.fact.list

sealed interface FactListAction {
    data class ShowError(val error: Exception) : FactListAction
}
