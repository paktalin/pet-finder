package com.paktalin.petfinder.ui.pet.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paktalin.petfinder.model.Pet
import com.paktalin.petfinder.usecase.ObservePetUseCase
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
class PetDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val observePetUseCase: ObservePetUseCase,
) : ViewModel() {

    private val args = PetDetailsDialogArgs.fromSavedStateHandle(savedStateHandle)

    private val _state = MutableStateFlow(PetDetailsState())
    val state: Flow<PetDetailsState> = _state

    private val _action = Channel<PetDetailsAction>()
    val action: Flow<PetDetailsAction> = _action.receiveAsFlow()

    private var pet: Pet? = null

    init {
        observePet(args.id)
    }

    fun trySend(event: PetDetailsEvent) {
        Timber.i("trySend $event")
    }

    private fun observePet(id: Long) = viewModelScope.launch {
        observePetUseCase(id).collect {
            pet = it
            modifyState()
        }
    }

    private fun modifyState() {
        _state.update { PetDetailsState(pet = pet) }
    }
}
