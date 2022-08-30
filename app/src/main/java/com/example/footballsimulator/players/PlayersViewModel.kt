package com.example.footballsimulator.players

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballsimulator.players.domain.PlayersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    playersRepository: PlayersRepository
) : ViewModel() {

    private val playerTeamId: String = checkNotNull(savedStateHandle["playerTeamId"])

    private val _uiState = MutableStateFlow(PlayersUiState())
    val uiState: StateFlow<PlayersUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            playersRepository.getTeamPlayers(playerTeamId).collect { players ->
                _uiState.update {
                    it.copy(players = players)
                }
            }
        }
    }
}
