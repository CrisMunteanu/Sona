package de.syntax_institut.androidabschlussprojekt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import de.syntax_institut.androidabschlussprojekt.data.local.SettingsDataStore
import de.syntax_institut.androidabschlussprojekt.presentation.components.BottomBar
import de.syntax_institut.androidabschlussprojekt.presentation.navigation.AppNavigation
import de.syntax_institut.androidabschlussprojekt.presentation.theme.SonaTheme
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
import de.syntax_institut.androidabschlussprojekt.domain.util.setAppLocale
import de.syntax_institut.androidabschlussprojekt.domain.util.LocalLocalizedContext


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val baseContext = LocalContext.current
            val scope = rememberCoroutineScope()
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            var isDarkMode by remember { mutableStateOf(false) }
            var currentLanguageCode by remember { mutableStateOf("de") }

            // Dynamischer Sprach-Kontext (reagiert auf Sprachwechsel)
            val localizedContext = remember(currentLanguageCode) {
                baseContext.setAppLocale(currentLanguageCode)
            }

            // 🌙 & 🌍 Einstellungen beim Start laden
            LaunchedEffect(Unit) {
                isDarkMode = SettingsDataStore.getDarkMode(baseContext)

                val savedLanguage = SettingsDataStore.getLanguage(baseContext)
                currentLanguageCode = when (savedLanguage) {
                    "Englisch" -> "en"
                    "Französisch" -> "fr"
                    "Spanisch" -> "es"
                    else -> "de"
                }

                baseContext.setAppLocale(currentLanguageCode) // optional, da lokalisiert unten
            }

            CompositionLocalProvider(LocalLocalizedContext provides localizedContext) {

                SonaTheme(darkTheme = isDarkMode) {
                    Scaffold(
                        bottomBar = {
                            if (currentRoute in listOf("start", "home", "favorites", "pose_list", "settings")) {
                                BottomBar(navController = navController)
                            }
                        }
                    ) { innerPadding ->
                        AppNavigation(
                            navController = navController,
                            modifier = Modifier.padding(innerPadding),
                            isDarkMode = isDarkMode,
                            onToggleDarkMode = {
                                isDarkMode = it
                                scope.launch {
                                    SettingsDataStore.saveDarkMode(baseContext, it)
                                }
                            },
                            currentLanguageCode = currentLanguageCode,
                            onLanguageChange = { newCode ->
                                currentLanguageCode = newCode
                                scope.launch {
                                    val label = when (newCode) {
                                        "en" -> "Englisch"
                                        "fr" -> "Französisch"
                                        "es" -> "Spanisch"
                                        else -> "Deutsch"
                                    }
                                    SettingsDataStore.saveLanguage(baseContext, label)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}