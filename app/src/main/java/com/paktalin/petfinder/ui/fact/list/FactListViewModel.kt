package com.paktalin.petfinder.ui.fact.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paktalin.petfinder.model.Fact
import com.paktalin.petfinder.ui.fact.list.FactListAction.ShowError
import com.paktalin.petfinder.usecase.ObserveFactsUseCase
import com.paktalin.petfinder.usecase.RefreshFactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FactListViewModel @Inject constructor(
    private val refreshFactsUseCase: RefreshFactsUseCase,
    private val observeFactsUseCase: ObserveFactsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(FactListState())
    val state: Flow<FactListState> = _state

    private val _action = Channel<FactListAction>()
    val action: Flow<FactListAction> = _action.receiveAsFlow()

    private var facts: List<Fact> = emptyList()

    fun trySend(event: FactListEvent) {
        Timber.i("trySend $event")
    }

    init {
        observeFacts()
        refreshFacts()
    }

    private fun refreshFacts() = viewModelScope.launch {
        when (val result = refreshFactsUseCase()) {
            is RefreshFactsUseCase.Result.Failure -> _action.send(ShowError(result.error))
            RefreshFactsUseCase.Result.Success -> Unit
        }
    }

    private fun observeFacts() = viewModelScope.launch {
        observeFactsUseCase().collect {
            facts = it
            modifyState()
        }
    }

    private fun modifyState() {
        _state.update { it.copy(facts = facts) }
    }
}
