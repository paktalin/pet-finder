package com.paktalin.petfinder.ui.pet.list

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.paktalin.petfinder.R
import com.paktalin.petfinder.databinding.PetListFragmentBinding
import com.paktalin.petfinder.ui.pet.filter.getFilterResult
import com.paktalin.petfinder.ui.pet.list.PetListAction.NavigateToDetails
import com.paktalin.petfinder.ui.pet.list.PetListAction.NavigateToFilters
import com.paktalin.petfinder.ui.pet.list.PetListAction.ShowError
import com.paktalin.petfinder.ui.pet.list.PetListEvent.FilterClick
import com.paktalin.petfinder.ui.pet.list.PetListEvent.FilterSelected
import com.paktalin.petfinder.ui.pet.list.PetListEvent.ItemClick
import com.paktalin.petfinder.ui.showError
import com.paktalin.petfinder.utils.navigate
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
        PetAdapter(onClick = { viewModel.send(ItemClick(it)) })
    }

    private fun setupViews() = with(view) {
        recyclerView.adapter = adapter
        toolbar.setOnFilterClickListener { viewModel.send(FilterClick) }
    }

    private fun onState(state: PetListState) = with(view) {
        Timber.i("onState: $state")
        adapter.submitList(state.pets)
        progressBar.isInvisible = !state.isLoadingVisible
        toolbar.title = state.toolbarText ?: getString(R.string.pet_list_title)
    }

    private fun onAction(action: PetListAction) {
        Timber.i("onAction: $action")
        when (action) {
            is ShowError -> showError(action.error)
            is NavigateToDetails -> navigateToDetails(action.id)
            is NavigateToFilters -> navigateToFilters(action.petTypes)
        }
    }

    private fun onFilterDestinationResult(filter: String) {
        viewModel.send(FilterSelected(filter))
    }

    private fun navigateToDetails(id: Long) {
        navigate(PetListFragmentDirections.details(id))
    }

    private fun navigateToFilters(petTypes: List<String>) {
        navigate(PetListFragmentDirections.filters(petTypes.toTypedArray()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        with(viewLifecycleOwner.lifecycleScope) {
            viewModel.state.onEach(::onState).launchIn(this)
            viewModel.action.onEach(::onAction).launchIn(this)
            getFilterResult(R.id.petListFragment)
                .onEach(::onFilterDestinationResult)
                .launchIn(this)
        }
    }
}

private fun Toolbar.setOnFilterClickListener(onClick: () -> Unit) {
    setOnMenuItemClickListener { item ->
        when (item.itemId) {
            R.id.filter -> onClick()
            else -> return@setOnMenuItemClickListener false
        }
        true
    }
}
