package com.paktalin.petfinder.ui.pet.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.paktalin.petfinder.R
import com.paktalin.petfinder.databinding.PetDetailsFragmentBinding
import com.paktalin.petfinder.utils.viewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class PetDetailsFragment : Fragment(R.layout.pet_details_fragment) {

    private val view by viewLifecycle(PetDetailsFragmentBinding::bind)
    private val viewModel: PetDetailsViewModel by viewModels()

    private fun setupViews() = with(view) {

    }

    private fun onState(state: PetDetailsState) = with(view) {
        Timber.i("onState: $state")
        name.text = state.pet?.name
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
}
