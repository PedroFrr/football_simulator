package com.example.footballsimulator.standings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballsimulator.standings.domain.StandingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StandingsViewModel @Inject constructor(
    standingsRepository: StandingsRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(StandingsUiState())
    val uiState: StateFlow<StandingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            standingsRepository.getStandings().collect { teamStandings ->
                _uiState.update {
                    it.copy(teamStandings = teamStandings)
                }
            }
        }
    }
}
