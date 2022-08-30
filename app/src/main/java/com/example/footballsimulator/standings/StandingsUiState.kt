package com.example.footballsimulator.standings

import com.example.footballsimulator.standings.domain.TeamStanding

data class StandingsUiState(
    val teamStandings: List<TeamStanding> = emptyList()
)
