package com.example.footballsimulator.fixtures

data class FixturesUiState(
    val onboardingStatus: OnboardingStatus = OnboardingStatus.ONBOARDING_DONE
) {
    enum class OnboardingStatus {
        ONBOARDING_REQUIRED,
        ONBOARDING_DONE
    }
}
