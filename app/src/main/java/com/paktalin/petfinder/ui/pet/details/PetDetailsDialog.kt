package com.paktalin.petfinder.ui.pet.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paktalin.petfinder.R
import com.paktalin.petfinder.databinding.PetDetailsDialogBinding
import com.paktalin.petfinder.model.Gender
import com.paktalin.petfinder.ui.pet.details.PetDetailsAction.Call
import com.paktalin.petfinder.ui.pet.details.PetDetailsEvent.CallClick
import com.paktalin.petfinder.ui.pet.loadPetPicture
import com.paktalin.petfinder.utils.viewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber


@AndroidEntryPoint
class PetDetailsDialog : BottomSheetDialogFragment() {

    private val view by viewLifecycle(PetDetailsDialogBinding::bind)
    private val viewModel: PetDetailsViewModel by viewModels()

    private fun setupViews() = with(view) {
        callButton.setOnClickListener { viewModel.send(CallClick) }
    }

    private fun onState(state: PetDetailsState) = with(view) {
        Timber.i("onState: $state")
        name.text = state.name
        pictureImage.loadPetPicture(state.pictureUrl)
        gender.setGenderDrawable(state.gender)
        description.text = state.description
        callButton.apply {
            isVisible = state.phoneNumber != null
            text = state.phoneNumber
        }
    }

    private fun onAction(action: PetDetailsAction) {
        Timber.i("onAction: $action")
        when (action) {
            is Call -> call(action.phoneNumber)
        }
    }

    private fun call(uri: String) {
        startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(uri)))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        with(viewLifecycleOwner.lifecycleScope) {
            viewModel.state.onEach(::onState).launchIn(this)
            viewModel.action.onEach(::onAction).launchIn(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.pet_details_dialog, container, false)
    }
}

private fun ImageView.setGenderDrawable(gender: Gender) {
    setImageDrawable(
        context.getDrawable(
            when (gender) {
                Gender.FEMALE -> R.drawable.ic_female
                Gender.MALE -> R.drawable.ic_male
                Gender.UNKNOWN -> R.drawable.ic_question_mark
            }
        )
    )
}
