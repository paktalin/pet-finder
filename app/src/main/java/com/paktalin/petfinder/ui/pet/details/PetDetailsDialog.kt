package com.paktalin.petfinder.ui.pet.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paktalin.petfinder.R
import com.paktalin.petfinder.databinding.PetDetailsDialogBinding
import com.paktalin.petfinder.model.Gender
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

    }

    private fun onState(state: PetDetailsState) = with(view) {
        Timber.i("onState: $state")
        state.pet?.let { pet ->
            name.text = pet.name
            pictureImage.loadPetPicture(pet.pictureUrl)
            gender.setGenderDrawable(pet.gender)
            description.text = pet.description
        }
    }

    private fun onAction(action: PetDetailsAction) {
        Timber.i("onAction: $action")
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
