package com.paktalin.petfinder.ui.fact.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.paktalin.petfinder.R
import com.paktalin.petfinder.databinding.FactListFragmentBinding
import com.paktalin.petfinder.utils.navigateUp
import com.paktalin.petfinder.utils.viewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class FactListFragment : Fragment(R.layout.fact_list_fragment) {

    private val view by viewLifecycle(FactListFragmentBinding::bind)
    private val viewModel: FactListViewModel by viewModels()
    private val adapter by viewLifecycle {
        FactAdapter(onClick = { item -> Timber.e("click: $item") })
    }

    private fun setupViews() = with(view) {
        toolbar.setNavigationOnClickListener { navigateUp() }
        recyclerView.adapter = adapter

    }

    private fun onState(state: FactListState) = with(view) {
        Timber.i("onState: $state")
        adapter.submitList(state.facts)
    }

    private fun onAction(action: FactListAction) {
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
