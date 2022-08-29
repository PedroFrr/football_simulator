package com.example.footballsimulator.fixtures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballsimulator.common.data.datastore.ApplicationPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FixturesViewModel @Inject constructor(
    applicationPreferences: ApplicationPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(FixturesUiState())
    val uiState: StateFlow<FixturesUiState> = _uiState.asStateFlow()

    init {
        // if the user didn't see the onboarding screen yet, redirect him
        viewModelScope.launch {
            applicationPreferences.isOnboarded.collect { hasCheckedOnboarding ->
                if (hasCheckedOnboarding) {
                    _uiState.update {
                        it.copy(onboardingStatus = FixturesUiState.OnboardingStatus.ONBOARDING_DONE)
                    }
                } else {
                    _uiState.update {
                        it.copy(onboardingStatus = FixturesUiState.OnboardingStatus.ONBOARDING_REQUIRED)
                    }
                }
            }
        }
    }
}
