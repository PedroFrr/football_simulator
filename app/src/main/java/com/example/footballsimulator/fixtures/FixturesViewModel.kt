package com.example.footballsimulator.fixtures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballsimulator.common.data.datastore.ApplicationPreferences
import com.example.footballsimulator.fixtures.domain.Fixture
import com.example.footballsimulator.fixtures.domain.repository.FixturesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FixturesViewModel @Inject constructor(
    applicationPreferences: ApplicationPreferences,
    fixturesRepository: FixturesRepository
) : ViewModel() {

    private val onboardingStatusStream: Flow<Boolean> = applicationPreferences.isOnboarded

    private val fixturesStream: Flow<List<Fixture>> = fixturesRepository.getFixtures()

    val uiState: StateFlow<FixturesUiState> =
        combine(
            fixturesStream,
            onboardingStatusStream,
        ) { fixtures, hasCheckedOnboarding ->
            val onboardingStatus = if (!hasCheckedOnboarding) {
                FixturesUiState.OnboardingStatus.ONBOARDING_REQUIRED
            } else {
                FixturesUiState.OnboardingStatus.ONBOARDING_DONE
            }
            FixturesUiState(
                onboardingStatus = onboardingStatus,
                fixtures = fixtures
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FixturesUiState()
        )
}
