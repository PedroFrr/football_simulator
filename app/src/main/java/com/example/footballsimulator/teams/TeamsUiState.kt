package com.example.footballsimulator.teams

import com.example.footballsimulator.teams.domain.Team

data class TeamsUiState(
    val teams: List<Team> = emptyList()
)
