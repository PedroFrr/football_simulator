package com.example.footballsimulator.fixtures

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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.footballsimulator.R
import com.example.footballsimulator.common.data.db.workers.FixturesDatabaseWorker
import com.example.footballsimulator.common.util.YYYY_MM_DD
import com.example.footballsimulator.common.util.formatToDate
import com.example.footballsimulator.databinding.FragmentFixturesBinding
import com.example.footballsimulator.fixtures.domain.Fixture
import com.example.footballsimulator.fixtures.domain.FixtureDataItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FixturesFragment : Fragment() {

    private var _binding: FragmentFixturesBinding? = null
    private val binding get() = _binding!!

    private val fixturesViewModel by viewModels<FixturesViewModel>()

    private val fixtureAdapter by lazy { FixturesListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFixturesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        setupObservables()
    }

    private fun setupUi() {
        setupListAdapter()

        binding.btnSimulateFixtures.setOnClickListener {
            fixturesViewModel.onSimulateScheduleClicked()
        }

        binding.btnResetFixtures.setOnClickListener {
            val resetFixturesWorker: WorkRequest =
                OneTimeWorkRequestBuilder<FixturesDatabaseWorker>()
                    .build()

            WorkManager
                .getInstance(requireContext())
                .enqueue(resetFixturesWorker)
        }
    }

    private fun setupObservables() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                fixturesViewModel.uiState.collect { uiState ->
                    if (uiState.onboardingStatus == FixturesUiState.OnboardingStatus.ONBOARDING_REQUIRED) {
                        findNavController().navigate(R.id.navigate_from_fixtures_to_onboarding)
                    }

                    fixtureAdapter.submitList(addAndConvertFixtureItems(uiState.fixtures))
                }
            }
        }
    }

    private fun setupListAdapter() {
        binding.rvFixtures.apply {
            adapter = fixtureAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            hasFixedSize()
        }
    }

    private fun addAndConvertFixtureItems(fixtures: List<Fixture>): List<FixtureDataItem> {
        val orderedFixtures = fixtures.sortedBy { it.date.formatToDate(YYYY_MM_DD) }
        val fixturesWithRounds = mutableListOf<FixtureDataItem>()

        // Loop through the fixtures list and add headers where we need them
        var currentRoundNumber = 1
        var currentHeader: String? = null
        orderedFixtures.forEach { fixture ->
            val fixtureDate = fixture.date
            fixtureDate.let { date ->
                if (date != currentHeader) {
                    fixturesWithRounds.add(FixtureDataItem.RoundItem(getString(R.string.round_number, currentRoundNumber)))
                    currentHeader = date
                    currentRoundNumber++
                }
            }
            fixturesWithRounds.add(FixtureDataItem.FixtureItem(fixture))
        }
        return fixturesWithRounds
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
