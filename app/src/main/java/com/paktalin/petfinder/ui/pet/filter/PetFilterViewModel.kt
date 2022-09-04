package com.paktalin.petfinder.ui.pet.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paktalin.petfinder.model.PetType
import com.paktalin.petfinder.ui.pet.filter.PetFilterAction.ShowError
import com.paktalin.petfinder.usecase.ObservePetTypesUseCase
import com.paktalin.petfinder.usecase.RefreshPetTypesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetFilterViewModel @Inject constructor(
    private val refreshPetTypesUseCase: RefreshPetTypesUseCase,
    private val observePetTypesUseCase: ObservePetTypesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(emptyList<PetType>())
    val state: Flow<List<PetType>> = _state

    private val _action = Channel<PetFilterAction>()
    val action: Flow<PetFilterAction> = _action.receiveAsFlow()

    init {
        observePetTypes()
        refreshPetTypes()
    }

    private fun observePetTypes() = viewModelScope.launch {
        observePetTypesUseCase().collect { petTypes -> _state.update { petTypes } }
    }

    private fun refreshPetTypes() = viewModelScope.launch {
        when (val result = refreshPetTypesUseCase()) {
            is RefreshPetTypesUseCase.Result.Failure -> _action.send(ShowError(result.error))
            RefreshPetTypesUseCase.Result.Success -> Unit
        }
    }
}
