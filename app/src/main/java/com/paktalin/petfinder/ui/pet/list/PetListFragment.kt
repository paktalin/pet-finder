package com.paktalin.petfinder.ui.pet.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.paktalin.petfinder.R
import com.paktalin.petfinder.databinding.PetListFragmentBinding
import com.paktalin.petfinder.utils.navigateUp
import com.paktalin.petfinder.utils.viewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class PetListFragment : Fragment(R.layout.pet_list_fragment) {

    private val view by viewLifecycle(PetListFragmentBinding::bind)
    private val viewModel: PetListViewModel by viewModels()
    private val adapter by viewLifecycle {
        PetAdapter(onClick = { item -> Timber.e("click: $item") })
    }

    private fun setupViews() = with(view) {
        toolbar.setNavigationOnClickListener { navigateUp() }
        recyclerView.adapter = adapter

    }

    private fun onState(state: PetListState) = with(view) {
        Timber.i("onState: $state")
        adapter.submitList(state.pets)
    }

    private fun onAction(action: PetListAction) {
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
