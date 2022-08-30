package com.example.footballsimulator.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.footballsimulator.databinding.FragmentTeamStandingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StandingsFragment : Fragment() {

    private var _binding: FragmentTeamStandingsBinding? = null
    private val binding get() = _binding!!

    private val standingsViewModel by viewModels<StandingsViewModel>()

    private val standingsListAdapter by lazy { StandingsListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTeamStandingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        setupObservables()
    }

    private fun setupUi() {
        setupListAdapter()
    }

    private fun setupObservables() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                standingsViewModel.uiState.collect { uiState ->
                    standingsListAdapter.convertHeaderAndSubmit(uiState.teamStandings)
                }
            }
        }
    }

    private fun setupListAdapter() {
        binding.rvTeamStandings.apply {
            adapter = standingsListAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            hasFixedSize()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
