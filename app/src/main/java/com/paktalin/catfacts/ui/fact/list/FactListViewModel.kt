package com.paktalin.catfacts.ui.fact.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paktalin.catfacts.usecase.RefreshFactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FactListViewModel @Inject constructor(
    private val refreshFactsUseCase: RefreshFactsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(FactListState)
    val state: Flow<FactListState> = _state

    private val _action = Channel<FactListAction>()
    val action: Flow<FactListAction> = _action.receiveAsFlow()

    fun trySend(event: FactListEvent) {
        Timber.i("trySend $event")
    }

    init {
        getFacts()
    }

    private fun getFacts() = viewModelScope.launch {
        refreshFactsUseCase()
    }
}
