package com.paktalin.petfinder.ui.pet.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paktalin.petfinder.model.Gender
import com.paktalin.petfinder.ui.pet.details.PetDetailsAction.Call
import com.paktalin.petfinder.ui.pet.details.PetDetailsEvent.CallClick
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

    private var name: String = ""
    private var description: String? = null
    private var pictureUrl: String? = null
    private var phoneNumber: String? = null
    private var gender: Gender = Gender.UNKNOWN

    init {
        observePet(args.id)
    }

    fun send(event: PetDetailsEvent) {
        Timber.i("send $event")
        when (event) {
            CallClick -> onCallClick()
        }
    }

    private fun observePet(id: Long) = viewModelScope.launch {
        observePetUseCase(id).collect { pet ->
            phoneNumber = pet?.phoneNumber
            pictureUrl = pet?.pictureUrl
            description = pet?.description
            name = pet?.name ?: ""
            gender = pet?.gender ?: Gender.UNKNOWN
            modifyState()
        }
    }

    private fun onCallClick() = viewModelScope.launch {
        phoneNumber?.let { phoneNumber -> _action.send(Call("tel:$phoneNumber")) }
    }

    private fun modifyState() {
        _state.update {
            PetDetailsState(
                name = name,
                description = description,
                pictureUrl = pictureUrl,
                phoneNumber = phoneNumber,
                gender = gender,
            )
        }
    }
}
