package com.example.footballsimulator.fixtures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballsimulator.common.data.datastore.ApplicationPreferences
import com.example.footballsimulator.fixtures.domain.Fixture
import com.example.footballsimulator.teams.domain.Team
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FixturesViewModel @Inject constructor(
    applicationPreferences: ApplicationPreferences
) : ViewModel() {

/*    private val _uiState = MutableStateFlow(FixturesUiState())
    val uiState: StateFlow<FixturesUiState> = _uiState.asStateFlow()*/

    private val onboardingStatusStream: Flow<Boolean> = applicationPreferences.isOnboarded

    private val fixturesStream: Flow<List<Fixture>> = flow {
        emit(mockFixtures)
    }

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
/*
    init {
        // if the user didn't see the onboarding screen yet, redirect him
        viewModelScope.launch {
            applicationPreferences.isOnboarded.collect { hasCheckedOnboarding ->
                if (!hasCheckedOnboarding) {
                    _uiState.update {
                        it.copy(onboardingStatus = FixturesUiState.OnboardingStatus.ONBOARDING_REQUIRED)
                    }
                }
            }
        }
        _uiState.update {
            it.copy(
                fixtures = mockFixtures
            )
        }
    }*/
}

private val mockFixtures = listOf(
    Fixture(
        date = "2022-08-03",
        homeTeam = Team(
            name = "Manchester United"
        ),
        homeTeamScore = 9,
        awayTeam = Team(
            name = "Arsenal"
        ),
        awayTeamScore = 3
    ),
    Fixture(
        date = "2022-08-01",
        homeTeam = Team(
            name = "Arsenal"
        ),
        homeTeamScore = 1,
        awayTeam = Team(
            name = "Leeds United"
        ),
        awayTeamScore = 0
    ),
    Fixture(
        date = "2022-08-01",
        homeTeam = Team(
            name = "Manchester United"
        ),
        homeTeamScore = 5,
        awayTeam = Team(
            name = "Chelsea"
        ),
        awayTeamScore = 0
    ),
    Fixture(
        date = "2022-08-02",
        homeTeam = Team(
            name = "Arsenal"
        ),
        homeTeamScore = 1,
        awayTeam = Team(
            name = "Manchester United"
        ),
        awayTeamScore = 3
    ),
    Fixture(
        date = "2022-08-03",
        homeTeam = Team(
            name = "Chelsea"
        ),
        homeTeamScore = 2,
        awayTeam = Team(
            name = "Leeds United"
        ),
        awayTeamScore = 0
    ),
    Fixture(
        date = "2022-08-02",
        homeTeam = Team(
            name = "Leeds United"
        ),
        homeTeamScore = 2,
        awayTeam = Team(
            name = "Chelsea"
        ),
        awayTeamScore = 0
    ),
)
