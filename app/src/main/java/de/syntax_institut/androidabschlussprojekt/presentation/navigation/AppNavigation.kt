package de.syntax_institut.androidabschlussprojekt.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.presentation.screens.favorites.FavoritesScreen
import de.syntax_institut.androidabschlussprojekt.presentation.screens.home.HomeScreen
import de.syntax_institut.androidabschlussprojekt.presentation.screens.info.MentalBenefitsDetailScreen
import de.syntax_institut.androidabschlussprojekt.presentation.screens.info.MentalBenefitsScreen
import de.syntax_institut.androidabschlussprojekt.presentation.screens.onboarding.OnboardingScreen
import de.syntax_institut.androidabschlussprojekt.presentation.screens.player.AudioPlayerScreen
import de.syntax_institut.androidabschlussprojekt.presentation.screens.pose.MeditationPoseDetailScreen
import de.syntax_institut.androidabschlussprojekt.presentation.screens.pose.MeditationPoseListScreen
import de.syntax_institut.androidabschlussprojekt.presentation.screens.quotes.QuoteDetailScreen
import de.syntax_institut.androidabschlussprojekt.presentation.screens.quotes.QuotesGalleryScreen
import de.syntax_institut.androidabschlussprojekt.presentation.screens.settings.SettingsScreen
import de.syntax_institut.androidabschlussprojekt.presentation.screens.splash.SplashScreen
import de.syntax_institut.androidabschlussprojekt.presentation.screens.start.CategoryStartScreen
import de.syntax_institut.androidabschlussprojekt.presentation.screens.breathe.BreathingScreen
import de.syntax_institut.androidabschlussprojekt.presentation.screens.timer.TimerScreen
import de.syntax_institut.androidabschlussprojekt.presentation.screens.journal.JournalScreen
import de.syntax_institut.androidabschlussprojekt.presentation.screens.favoritequote.FavoriteQuoteScreen
import de.syntax_institut.androidabschlussprojekt.presentation.screens.meditation.MeditationHistoryScreen
import de.syntax_institut.androidabschlussprojekt.domain.model.MeditationItem
import android.net.Uri
import de.syntax_institut.androidabschlussprojekt.presentation.screens.pixabay.PixabayMusicScreen
import de.syntax_institut.androidabschlussprojekt.presentation.screens.streamplayer.StreamPlayerScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    isDarkMode: Boolean,
    onToggleDarkMode: (Boolean) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = "splash",
        modifier = modifier
    ) {
        // Einstieg & Onboarding
        composable("splash") { SplashScreen(navController) }
        composable("onboarding") { OnboardingScreen(navController) }

        // Kategorien / Start
        composable("start") {
            CategoryStartScreen(
                onItemSelected = { selectedItem ->
                    navController.navigate("player/${selectedItem.audioFile}/${selectedItem.imageResId}")
                },
                onPixabayClicked = {
                    navController.navigate("pixabay")
                }
            )
        }

        //Lokaler AudioPlayer
        composable(
            route = "player/{fileName}/{imageResId}",
            arguments = listOf(
                navArgument("fileName") { type = NavType.StringType },
                navArgument("imageResId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val fileName = backStackEntry.arguments?.getString("fileName") ?: "sleep_peaceful1.mp3"
            val imageResId = backStackEntry.arguments?.getInt("imageResId") ?: R.drawable.sleep1
            AudioPlayerScreen(
                fileName = fileName,
                imageResId = imageResId,
                navController = navController
            )
        }

        //Timer
        composable(
            route = "timer/{fileName}",
            arguments = listOf(navArgument("fileName") { type = NavType.StringType })
        ) { backStackEntry ->
            val fileName = backStackEntry.arguments?.getString("fileName") ?: "sleep_peaceful1.mp3"
            TimerScreen(fileName = fileName, navController = navController)
        }

        // 🎵 Online-Musik (Pixabay)
        composable("pixabay") {
            PixabayMusicScreen(
                onPlayTrack = { track ->
                    navController.navigate("streamPlayer/${Uri.encode(track.audioUrl)}")
                }
            )
        }
        composable(
            route = "streamPlayer/{url}",
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""
            StreamPlayerScreen(
                audioUrl = Uri.decode(url),
                navController = navController
            )
        }

        //Zitate
        composable("quotes") { QuotesGalleryScreen(navController) }
        composable("favorite_quotes") { FavoriteQuoteScreen(navController) }
        composable(
            route = "quote_detail/{quoteText}/{quoteAuthor}",
            arguments = listOf(
                navArgument("quoteText") { type = NavType.StringType },
                navArgument("quoteAuthor") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val quoteText = backStackEntry.arguments?.getString("quoteText") ?: ""
            val quoteAuthor = backStackEntry.arguments?.getString("quoteAuthor") ?: ""
            QuoteDetailScreen(quoteText = quoteText, quoteAuthor = quoteAuthor)
        }

        //Tagebuch
        composable("journal") { JournalScreen(navController) }

        // Mental-Benefits
        composable("mental_benefits") { MentalBenefitsScreen(navController) }
        composable("mental_benefits_detail") { MentalBenefitsDetailScreen(navController) }

        //Meditationsstellungen
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
            MeditationPoseDetailScreen(poseId = poseId, navController = navController)
        }

        //Verlauf
        composable("meditation_history") {
            MeditationHistoryScreen(navController = navController)
        }

        //Atmung
        composable("breathing") { BreathingScreen() }

        // Einstellungen
        composable("settings") {
            SettingsScreen(
                isDarkMode = isDarkMode,
                onToggleDarkMode = onToggleDarkMode
            )
        }

        // Favoriten
        composable("home") { HomeScreen(navController) }
        composable("favorites") { FavoritesScreen(navController) }
    }
}