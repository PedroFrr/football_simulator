package com.example.footballsimulator.teams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.footballsimulator.databinding.FragmentTeamsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TeamsFragment : Fragment() {

    private var _binding: FragmentTeamsBinding? = null
    private val binding get() = _binding!!

    private val fixturesViewModel by viewModels<TeamsViewModel>()

    private val teamsListAdapter by lazy { TeamsListAdapter(::onTeamClicked) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamsBinding.inflate(inflater, container, false)
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
                fixturesViewModel.uiState.collect { uiState ->
                    teamsListAdapter.submitList(uiState.teams)
                }
            }
        }
    }

    private fun setupListAdapter() {
        binding.rvTeams.apply {
            adapter = teamsListAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            hasFixedSize()
        }
    }

    private fun onTeamClicked(teamId: String) {
        val directions = TeamsFragmentDirections.navigateFromTeamsToPlayers(teamId)
        findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
