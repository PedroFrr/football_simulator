package com.example.footballsimulator.players.domain

import kotlinx.coroutines.flow.Flow

interface PlayersRepository {
    fun getTeamPlayers(teamId: String): Flow<List<Player>>
}