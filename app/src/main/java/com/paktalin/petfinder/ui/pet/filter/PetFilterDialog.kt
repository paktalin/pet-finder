package com.paktalin.petfinder.ui.pet.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paktalin.petfinder.R
import com.paktalin.petfinder.databinding.PetFilterDialogBinding
import com.paktalin.petfinder.model.PetType
import com.paktalin.petfinder.utils.getDestinationResult
import com.paktalin.petfinder.utils.setDestinationResult
import com.paktalin.petfinder.utils.viewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class PetFilterDialog : BottomSheetDialogFragment() {

    private val view by viewLifecycle(PetFilterDialogBinding::bind)
    private val viewModel: PetFilterViewModel by viewModels()
    private val adapter by viewLifecycle {
        PetFilterAdapter(onClick = { onItemClick(it) })
    }

    private fun setupViews() = with(view) {
        recyclerView.adapter = adapter
    }

    private fun onState(state: List<PetType>) = with(view) {
        Timber.i("onState: $state")
        adapter.submitList(state)
    }

    private fun onItemClick(type: String) {
        setDestinationResult(RESULT, type)
        dismiss()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.pet_filter_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        viewModel.state.onEach(::onState).launchIn(viewLifecycleOwner.lifecycleScope)
    }
}

fun Fragment.getFilterResult(
    @IdRes destinationId: Int
): Flow<String> = getDestinationResult(RESULT, destinationId)

private const val RESULT = "BookActionsDialog.result"
