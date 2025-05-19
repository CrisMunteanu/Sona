package de.syntax_institut.androidabschlussprojekt.presentation.screens.home


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import de.syntax_institut.androidabschlussprojekt.presentation.components.BreathingCard
import de.syntax_institut.androidabschlussprojekt.presentation.components.MeditationCard
import de.syntax_institut.androidabschlussprojekt.presentation.components.MoodSuggestionCard
import de.syntax_institut.androidabschlussprojekt.presentation.components.QuoteGalleryCard
import de.syntax_institut.androidabschlussprojekt.presentation.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: MainViewModel = koinViewModel()
    val categories = viewModel.allItems

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        //  Atemübung (Card)
        item {
            BreathingCard(
                onClick = { navController.navigate("breathing") }
            )
        }

        // Zitate-Galerie (Card)
        item {
            QuoteGalleryCard(
                onClick = { navController.navigate("quotes") }
            )
        }

        // Stimmung + Vorschlag (NEUE Card!)
        item {
            MoodSuggestionCard(
                navController = navController,
                viewModel = viewModel
            )
        }
        // 🎵 Alle Meditations-Kategorien
        items(categories) { item ->
            MeditationCard(item = item) {
                navController.navigate("player/${item.audioFile}/${item.imageResId}")
            }
        }
    }
}


