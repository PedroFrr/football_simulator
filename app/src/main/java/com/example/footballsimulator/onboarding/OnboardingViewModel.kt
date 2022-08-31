package com.example.footballsimulator.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballsimulator.R
import com.example.footballsimulator.common.data.datastore.ApplicationPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val applicationPreferences: ApplicationPreferences,
) : ViewModel() {
    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                onboardingScreenInformation = listOf(
                    R.string.onboarding_first_screen_information,
                    R.string.onboarding_second_screen_information,
                    R.string.onboarding_third_screen_information
                )
            )
        }
    }

    fun onDoneButtonClicked() {
        viewModelScope.launch {
            applicationPreferences.setOnboardCheck(true)
            _uiState.update {
                it.copy(userCheckedOnboarding = true)
            }
        }
    }
}
