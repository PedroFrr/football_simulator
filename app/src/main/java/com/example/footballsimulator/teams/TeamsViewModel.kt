package com.example.footballsimulator.teams

import androidx.lifecycle.ViewModel
import com.example.footballsimulator.teams.domain.Team
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TeamsViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(TeamsUiState())
    val uiState: StateFlow<TeamsUiState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(teams = mockTeams)
        }
    }
}

val mockTeams = listOf(
    Team(name = "Team1"),
    Team(name = "Team2"),
    Team(name = "Team3"),
    Team(name = "Team4"),
)