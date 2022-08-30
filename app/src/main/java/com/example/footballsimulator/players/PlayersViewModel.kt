package com.example.footballsimulator.players

import androidx.lifecycle.ViewModel
import com.example.footballsimulator.players.domain.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(PlayersUiState())
    val uiState: StateFlow<PlayersUiState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(players = mockPlayers)
        }
    }
}

private val mockPlayers = listOf(
    Player(
        playerId = "1",
        teamId = "Sss",
        name = "Joao"
    ),
    Player(
        playerId = "2",
        teamId = "Sss",
        name = "Manuel"
    ),
    Player(
        playerId = "3",
        teamId = "Sss",
        name = "Joaquim"
    ),
    Player(
        playerId = "4",
        teamId = "Sss",
        name = "maria"
    ),
    Player(
        playerId = "5",
        teamId = "Sss",
        name = "Pedro"
    ),
)
