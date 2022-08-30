package com.example.footballsimulator.onboarding.viewpager.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballsimulator.common.data.datastore.ApplicationPreferences
import com.example.footballsimulator.common.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingScreenViewModel @Inject constructor(
    private val applicationPreferences: ApplicationPreferences
) : ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onDoneButtonClicked() {
        viewModelScope.launch {
            applicationPreferences.setOnboardCheck(true)
            _uiEvent.send(UiEvent.Success)
        }
    }
}
