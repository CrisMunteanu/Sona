package de.syntax_institut.androidabschlussprojekt.presentation.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.syntax_institut.androidabschlussprojekt.data.local.OnboardingPreferences
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    isDarkMode: Boolean,
    onToggleDarkMode: (Boolean) -> Unit,
    currentLanguageCode: String,
    onLanguageChange: (String) -> Unit
) {
    val languageOptions = listOf(
        "Deutsch" to "de",
        "Englisch" to "en",
        "Französisch" to "fr",
        "Spanisch" to "es"
    )

    val selectedLanguage = languageOptions.find { it.second == currentLanguageCode }?.first ?: "Deutsch"
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

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
                        onLanguageChange(code) // 🔄 Sprache ändern
                    }
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = label)
                if (selectedLanguage == label) {
                    Text("✓", color = MaterialTheme.colorScheme.primary)
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
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