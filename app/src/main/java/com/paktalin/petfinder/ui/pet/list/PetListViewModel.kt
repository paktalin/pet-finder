package com.paktalin.petfinder.ui.pet.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paktalin.petfinder.model.Pet
import com.paktalin.petfinder.ui.pet.list.PetListAction.NavigateToDetails
import com.paktalin.petfinder.ui.pet.list.PetListAction.NavigateToFilters
import com.paktalin.petfinder.ui.pet.list.PetListAction.ShowError
import com.paktalin.petfinder.ui.pet.list.PetListEvent.*
import com.paktalin.petfinder.usecase.ObservePetsUseCase
import com.paktalin.petfinder.usecase.RefreshPetsUseCase
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
class PetListViewModel @Inject constructor(
    private val refreshPetsUseCase: RefreshPetsUseCase,
    private val observePetsUseCase: ObservePetsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(PetListState())
    val state: Flow<PetListState> = _state

    private val _action = Channel<PetListAction>()
    val action: Flow<PetListAction> = _action.receiveAsFlow()

    private var pets: List<Pet> = emptyList()

    fun send(event: PetListEvent) {
        Timber.i("send $event")
        when (event) {
            FilterClick -> onFilterClick()
            is ItemClick -> onItemClick(event.id)
            is FilterSelected -> onFilterSelected(event.filter)
        }
    }

    init {
        observePets()
        refreshPets()
    }

    private fun refreshPets() = viewModelScope.launch {
        when (val result = refreshPetsUseCase()) {
            is RefreshPetsUseCase.Result.Failure -> _action.send(ShowError(result.error))
            RefreshPetsUseCase.Result.Success -> Unit
        }
    }

    private fun observePets() = viewModelScope.launch {
        observePetsUseCase().collect {
            pets = it
            modifyState()
        }
    }

    private fun onFilterClick() = viewModelScope.launch {
        _action.send(NavigateToFilters)
    }

    private fun onItemClick(id: Long) = viewModelScope.launch {
        _action.send(NavigateToDetails(id))
    }

    private fun onFilterSelected(filter: String) {
        
    }

    private fun modifyState() {
        _state.update { it.copy(pets = pets) }
    }
}
