package de.syntax_institut.androidabschlussprojekt.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "onboarding")

object OnboardingPreferences {
    private val HAS_SEEN_ONBOARDING = booleanPreferencesKey("has_seen_onboarding")

    suspend fun hasSeenOnboarding(context: Context): Boolean {
        return context.dataStore.data.map { prefs ->
            prefs[HAS_SEEN_ONBOARDING] ?: false
        }.first()
    }

    suspend fun setOnboardingSeen(context: Context) {
        context.dataStore.edit { prefs ->
            prefs[HAS_SEEN_ONBOARDING] = true
        }
    }

    suspend fun resetOnboardingSeen(context: Context) {
        context.dataStore.edit { prefs ->
            prefs[HAS_SEEN_ONBOARDING] = false
        }
    }

}