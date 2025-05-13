package de.syntax_institut.androidabschlussprojekt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import de.syntax_institut.androidabschlussprojekt.data.local.SettingsDataStore
import de.syntax_institut.androidabschlussprojekt.presentation.components.BottomBar
import de.syntax_institut.androidabschlussprojekt.presentation.navigation.AppNavigation
import de.syntax_institut.androidabschlussprojekt.presentation.theme.SonaTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Sprache vor setContent setzen
        val languageCode = runBlocking {
            SettingsDataStore.getLanguageCode(applicationContext)
        }
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        setContent {
            val context = LocalContext.current
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val scope = rememberCoroutineScope()
            var isDarkMode by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                isDarkMode = SettingsDataStore.getDarkMode(context)
            }

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
                                SettingsDataStore.saveDarkMode(context, it)
                            }
                        },

                    )
                }
            }
        }
    }
}