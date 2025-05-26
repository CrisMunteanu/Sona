package de.syntax_institut.androidabschlussprojekt.data.local

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// DataStore-Instanz
val Context.settingsDataStore by preferencesDataStore(name = "user_settings")

object SettingsDataStore {

    private val LANGUAGE_KEY = stringPreferencesKey("language")
    private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    private val ONBOARDING_SEEN_KEY = booleanPreferencesKey("onboarding_seen")

    //  Reminder-Keys
    private val REMINDER_ENABLED_KEY = booleanPreferencesKey("reminder_enabled")
    private val REMINDER_HOUR_KEY = intPreferencesKey("reminder_hour")
    private val REMINDER_MINUTE_KEY = intPreferencesKey("reminder_minute")

    // Onboarding
    suspend fun setOnboardingSeen(context: Context, seen: Boolean) {
        context.settingsDataStore.edit { prefs ->
            prefs[ONBOARDING_SEEN_KEY] = seen
        }
    }

    suspend fun getOnboardingSeen(context: Context): Boolean {
        return context.settingsDataStore.data
            .map { it[ONBOARDING_SEEN_KEY] ?: false }
            .first()
    }

    // Dark Mode speichern/lesen
    suspend fun saveDarkMode(context: Context, enabled: Boolean) {
        context.settingsDataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = enabled
        }
    }

    suspend fun getDarkMode(context: Context): Boolean {
        return context.settingsDataStore.data
            .map { preferences -> preferences[DARK_MODE_KEY] ?: false }
            .first()
    }

    // Sprache speichern/lesen
    suspend fun getLanguageCode(context: Context): String {
        return context.settingsDataStore.data
            .map { it[LANGUAGE_KEY] ?: "de" }
            .first()
    }

    suspend fun saveLanguageCode(context: Context, code: String) {
        context.settingsDataStore.edit { it[LANGUAGE_KEY] = code }
    }

    //  Reminder speichern/lesen
    suspend fun saveReminderEnabled(context: Context, enabled: Boolean) {
        context.settingsDataStore.edit { prefs ->
            prefs[REMINDER_ENABLED_KEY] = enabled
        }
    }

    suspend fun getReminderEnabled(context: Context): Boolean {
        return context.settingsDataStore.data
            .map { it[REMINDER_ENABLED_KEY] ?: false }
            .first()
    }

    suspend fun saveReminderTime(context: Context, hour: Int, minute: Int) {
        context.settingsDataStore.edit { prefs ->
            prefs[REMINDER_HOUR_KEY] = hour
            prefs[REMINDER_MINUTE_KEY] = minute
        }
    }

    suspend fun getReminderHour(context: Context): Int {
        return context.settingsDataStore.data
            .map { it[REMINDER_HOUR_KEY] ?: 8 }
            .first()
    }

    suspend fun getReminderMinute(context: Context): Int {
        return context.settingsDataStore.data
            .map { it[REMINDER_MINUTE_KEY] ?: 0 }
            .first()
    }
}