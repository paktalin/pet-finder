package com.paktalin.petfinder.ui.pet.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paktalin.petfinder.model.PetType
import com.paktalin.petfinder.usecase.GetPetTypesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetFilterViewModel @Inject constructor(
    private val getPetTypesUseCase: GetPetTypesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(emptyList<PetType>())
    val state: Flow<List<PetType>> = _state

    init {
        getPetTypes()
    }

    private fun getPetTypes() = viewModelScope.launch {
        _state.update { getPetTypesUseCase() }
    }
}
