package de.syntax_institut.androidabschlussprojekt.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.presentation.screens.start.CategoryStartScreen
import de.syntax_institut.androidabschlussprojekt.presentation.screens.favorites.FavoritesScreen
import de.syntax_institut.androidabschlussprojekt.presentation.screens.home.HomeScreen
import de.syntax_institut.androidabschlussprojekt.presentation.screens.onboarding.OnboardingScreen
import de.syntax_institut.androidabschlussprojekt.presentation.screens.player.AudioPlayerScreen
import de.syntax_institut.androidabschlussprojekt.presentation.screens.pose.MeditationPoseDetailScreen
import de.syntax_institut.androidabschlussprojekt.presentation.screens.pose.MeditationPoseListScreen
import de.syntax_institut.androidabschlussprojekt.presentation.screens.quotes.QuotesGalleryScreen
import de.syntax_institut.androidabschlussprojekt.presentation.screens.settings.SettingsScreen
import de.syntax_institut.androidabschlussprojekt.presentation.screens.splash.SplashScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    isDarkMode: Boolean,
    onToggleDarkMode: (Boolean) -> Unit,
    currentLanguageCode: String,
    onLanguageChange: (String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = "splash",
        modifier = modifier
    ) {
        composable("splash") {
            SplashScreen(navController)
        }

        composable("onboarding") {
            OnboardingScreen {
                navController.navigate("start") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }

        composable("start") {
            CategoryStartScreen(
                onItemSelected = { selectedItem ->
                    navController.navigate("player/${selectedItem.audioFile}/${selectedItem.imageResId}")
                }
            )
        }

        composable("home") {
            HomeScreen(navController)
        }

        composable("favorites") {
            FavoritesScreen(navController)
        }

        composable("quotes") {
            QuotesGalleryScreen()
        }

        composable(
            route = "player/{fileName}/{imageResId}",
            arguments = listOf(
                navArgument("fileName") { defaultValue = "sleep_peaceful1.mp3" },
                navArgument("imageResId") { defaultValue = R.drawable.sleep1 }
            )
        ) { backStackEntry ->
            val fileName = backStackEntry.arguments?.getString("fileName") ?: "sleep_peaceful1.mp3"
            val imageResId = backStackEntry.arguments?.getInt("imageResId") ?: R.drawable.sleep1
            AudioPlayerScreen(fileName = fileName, imageResId = imageResId)
        }

        composable("pose_list") {
            MeditationPoseListScreen { poseId ->
                navController.navigate("pose_detail/$poseId")
            }
        }

        composable(
            route = "pose_detail/{poseId}",
            arguments = listOf(navArgument("poseId") { type = NavType.IntType })
        ) { backStackEntry ->
            val poseId = backStackEntry.arguments?.getInt("poseId") ?: -1
            MeditationPoseDetailScreen(poseId = poseId)
        }

        composable("settings") {
            SettingsScreen(
                isDarkMode = isDarkMode,
                onToggleDarkMode = onToggleDarkMode,
                currentLanguageCode = currentLanguageCode,
                onLanguageChange = onLanguageChange
            )
        }
    }
}