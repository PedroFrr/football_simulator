package com.example.footballsimulator.teams.domain

import kotlinx.coroutines.flow.Flow

interface TeamsRepository {
    fun getTeams(): Flow<List<Team>>
}
