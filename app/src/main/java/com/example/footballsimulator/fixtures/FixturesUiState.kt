package com.example.footballsimulator.fixtures

import com.example.footballsimulator.fixtures.domain.Fixture

data class FixturesUiState(
    val onboardingStatus: OnboardingStatus = OnboardingStatus.ONBOARDING_DONE,
    val fixtures: List<Fixture> = emptyList()
) {
    enum class OnboardingStatus {
        ONBOARDING_REQUIRED,
        ONBOARDING_DONE
    }
}
