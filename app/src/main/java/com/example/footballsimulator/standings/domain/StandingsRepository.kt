package com.example.footballsimulator.standings.domain

import kotlinx.coroutines.flow.Flow

interface StandingsRepository {
    suspend fun getStandings(): Flow<List<TeamStanding>>
}
