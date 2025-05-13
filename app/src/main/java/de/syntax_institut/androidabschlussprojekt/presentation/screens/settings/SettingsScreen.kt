package de.syntax_institut.androidabschlussprojekt.presentation.screens.settings

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.syntax_institut.androidabschlussprojekt.data.local.OnboardingPreferences
import de.syntax_institut.androidabschlussprojekt.data.local.SettingsDataStore
import de.syntax_institut.androidabschlussprojekt.domain.util.setLocaleAndRestart
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    isDarkMode: Boolean,
    onToggleDarkMode: (Boolean) -> Unit
) {
    val languageOptions = listOf(
        "Deutsch" to "de",
        "Englisch" to "en",
        "Französisch" to "fr",
        "Spanisch" to "es"
    )

    val context = LocalContext.current
    val activity = context as? Activity
    val scope = rememberCoroutineScope()

    var selectedLanguageCode by remember { mutableStateOf("de") }

    LaunchedEffect(Unit) {
        selectedLanguageCode = SettingsDataStore.getLanguageCode(context)
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Einstellungen",
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 22.sp)
        )

        Text("Sprache", fontSize = 18.sp)
        languageOptions.forEach { (label, code) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        scope.launch {
                            SettingsDataStore.saveLanguageCode(context, code)
                            activity?.let { setLocaleAndRestart(it, code) }
                        }
                    }
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = label)
                if (selectedLanguageCode == code) {
                    Text("✓", color = MaterialTheme.colorScheme.primary)
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Dark Mode", fontSize = 18.sp)
            Switch(
                checked = isDarkMode,
                onCheckedChange = onToggleDarkMode
            )
        }

        Button(
            onClick = {
                scope.launch {
                    OnboardingPreferences.resetOnboardingSeen(context)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Onboarding zurücksetzen")
        }
    }
}