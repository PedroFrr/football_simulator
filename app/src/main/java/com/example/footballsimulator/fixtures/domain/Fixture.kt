package com.example.footballsimulator.fixtures.domain

import com.example.footballsimulator.teams.domain.Team
import java.util.UUID

data class Fixture(
    val fixtureId: String = UUID.randomUUID().toString(),
    val date: String,
    val homeTeam: Team,
    val awayTeam: Team,
    val homeTeamScore: Int?,
    val awayTeamScore: Int?
)
