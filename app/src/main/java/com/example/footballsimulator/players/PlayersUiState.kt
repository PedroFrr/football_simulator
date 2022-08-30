package com.example.footballsimulator.players

import com.example.footballsimulator.players.domain.Player

data class PlayersUiState(
    val players: List<Player> = emptyList()
)
