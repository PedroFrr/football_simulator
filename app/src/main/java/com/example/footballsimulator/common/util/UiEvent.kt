package com.example.footballsimulator.common.util

sealed class UiEvent {
    object Success : UiEvent()
    object NavigateUp : UiEvent()
}
