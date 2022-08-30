package com.example.footballsimulator.standings.domain

import com.example.footballsimulator.teams.domain.Team

data class TeamStanding(
    val standingPosition: Int,
    val goalsScored: Int,
    val goalsConceded: Int,
    val points: Int,
    val team: Team,
)
