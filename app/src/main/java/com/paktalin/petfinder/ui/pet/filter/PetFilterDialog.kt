package com.paktalin.petfinder.ui.pet.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paktalin.petfinder.R
import com.paktalin.petfinder.databinding.PetFilterDialogBinding
import com.paktalin.petfinder.utils.getDestinationResult
import com.paktalin.petfinder.utils.setDestinationResult
import com.paktalin.petfinder.utils.viewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow

@AndroidEntryPoint
class PetFilterDialog : BottomSheetDialogFragment() {

    private val view by viewLifecycle(PetFilterDialogBinding::bind)
    private val args by navArgs<PetFilterDialogArgs>()
    private val adapter by viewLifecycle {
        PetFilterAdapter(onClick = { onItemClick(it) })
    }

    private fun setupViews() = with(view) {
        recyclerView.adapter = adapter
        adapter.submitList(args.petTypes.toList())
        resetFiltersButton.setOnClickListener { onResetFiltersClick() }
    }

    private fun onItemClick(type: String) {
        setDestinationResult(RESULT, type)
        dismiss()
    }

    private fun onResetFiltersClick() {
        setDestinationResult(RESULT, "all")
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
    }
}

fun Fragment.getFilterResult(
    @IdRes destinationId: Int
): Flow<String> = getDestinationResult(RESULT, destinationId)

private const val RESULT = "PetFilterDialog.result"
