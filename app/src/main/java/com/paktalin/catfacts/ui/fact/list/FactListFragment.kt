package com.paktalin.catfacts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ee.elisa.raamat.utils.viewLifecycle
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class FactListFragment : Fragment(R.layout.fact_list_fragment) {

    private val view by viewLifecycle(FactsListFragmentBinding::bind)
    private val viewModel: FactListViewModel by viewModels()

    private fun setupViews() = with(view) {

    }

    private fun onState(state: FactListState) = with(view) {
        Timber.i("onState: $state")
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
