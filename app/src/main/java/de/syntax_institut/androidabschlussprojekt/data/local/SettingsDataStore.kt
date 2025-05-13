package de.syntax_institut.androidabschlussprojekt.data.local



import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// DataStore-Instanz
val Context.settingsDataStore by preferencesDataStore(name = "user_settings")

object SettingsDataStore {

    private val LANGUAGE_KEY = stringPreferencesKey("language")
    private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")




    // Dark Mode speichern
    suspend fun saveDarkMode(context: Context, enabled: Boolean) {
        context.settingsDataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = enabled
        }
    }

    //  Dark Mode abrufen
    suspend fun getDarkMode(context: Context): Boolean {
        return context.settingsDataStore.data
            .map { preferences -> preferences[DARK_MODE_KEY] ?: false }
            .first()
    }
    // In SettingsDataStore.kt
    suspend fun getLanguageCode(context: Context): String {
        return context.dataStore.data.map { it[LANGUAGE_KEY] ?: "de" }.first()
    }

    suspend fun saveLanguageCode(context: Context, code: String) {
        context.dataStore.edit { it[LANGUAGE_KEY] = code }
    }




}