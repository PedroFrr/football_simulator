package com.example.footballsimulator.standings.domain

import com.example.footballsimulator.teams.domain.Team
import java.util.*

data class TeamStanding(
    val standingPosition: Int,
    val team: Team
)
