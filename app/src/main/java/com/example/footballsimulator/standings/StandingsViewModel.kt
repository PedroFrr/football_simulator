package com.example.footballsimulator.standings

import androidx.lifecycle.ViewModel
import com.example.footballsimulator.standings.domain.TeamStanding
import com.example.footballsimulator.teams.domain.Team
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class StandingsViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(StandingsUiState())
    val uiState: StateFlow<StandingsUiState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(teamStandings = mockTeamStandings)
        }
    }
}

val mockTeamStandings = listOf(
    TeamStanding(1, Team(name = "Team1")),
    TeamStanding(2, Team(name = "Team2")),
    TeamStanding(3, Team(name = "Team3")),
    TeamStanding(4, Team(name = "Team4")),
)
