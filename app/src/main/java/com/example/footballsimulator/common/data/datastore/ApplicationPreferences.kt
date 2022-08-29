package com.example.footballsimulator.common.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ApplicationPreferences @Inject constructor(
    private val context: Context
) {
    val isOnboarded: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            // No type safety.
            preferences[ONBOARDING_CHECK] ?: false
        }

    suspend fun setOnboardCheck(hasCheckedOnboarding: Boolean) {
        context.dataStore.edit { settings ->
            settings[ONBOARDING_CHECK] = hasCheckedOnboarding
        }
    }

    companion object {
        private val ONBOARDING_CHECK = booleanPreferencesKey("onboarding_check")
    }
}