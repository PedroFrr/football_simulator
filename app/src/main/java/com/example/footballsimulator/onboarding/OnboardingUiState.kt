package com.example.footballsimulator.onboarding

data class OnboardingUiState(
    val onboardingScreenInformation: List<Int> = emptyList(),
    val userCheckedOnboarding: Boolean = false
)
