package de.syntax_institut.androidabschlussprojekt.presentation.screens.settings

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.data.local.OnboardingPreferences
import de.syntax_institut.androidabschlussprojekt.data.local.SettingsDataStore
import de.syntax_institut.androidabschlussprojekt.domain.util.setLocaleAndRestart
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    isDarkMode: Boolean,
    onToggleDarkMode: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val scope = rememberCoroutineScope()

    var selectedLanguageCode by remember { mutableStateOf("de") }

    LaunchedEffect(Unit) {
        selectedLanguageCode = SettingsDataStore.getLanguageCode(context)
    }

    val emojiLanguages = listOf(
        "🇩🇪" to "de",
        "🇬🇧" to "en",
        "🇫🇷" to "fr",
        "🇪🇸" to "es"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = stringResource(R.string.settings),
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 22.sp)
        )

        Text(stringResource(R.string.language), fontSize = 18.sp)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            emojiLanguages.forEach { (emoji, code) ->
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(
                            if (selectedLanguageCode == code)
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                            else
                                Color.Transparent
                        )
                        .clickable {
                            scope.launch {
                                SettingsDataStore.saveLanguageCode(context, code)
                                activity?.let { setLocaleAndRestart(it, code) }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = emoji, fontSize = 28.sp)
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.dark_mode), fontSize = 18.sp)
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
            Text(stringResource(R.string.reset_onboarding))
        }
    }
}