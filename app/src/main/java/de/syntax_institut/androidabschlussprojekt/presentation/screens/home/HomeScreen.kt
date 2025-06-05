package de.syntax_institut.androidabschlussprojekt.presentation.screens.home


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import de.syntax_institut.androidabschlussprojekt.presentation.components.BreathingCard
import de.syntax_institut.androidabschlussprojekt.presentation.components.CosmicMeditationButton
import de.syntax_institut.androidabschlussprojekt.presentation.components.MeditationCard
import de.syntax_institut.androidabschlussprojekt.presentation.components.MoodSuggestionCard
import de.syntax_institut.androidabschlussprojekt.presentation.components.QuoteGalleryCard
import de.syntax_institut.androidabschlussprojekt.presentation.viewmodel.MainViewModel
import de.syntax_institut.androidabschlussprojekt.presentation.components.JournalButton
import de.syntax_institut.androidabschlussprojekt.presentation.components.StartScreenCard
import de.syntax_institut.androidabschlussprojekt.presentation.theme.ElegantRed
import de.syntax_institut.androidabschlussprojekt.presentation.theme.SoftPurple
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
        item {
            StartScreenCard(
                onClick = {
                    navController.navigate("start")
                }
            )
        }

        // Stimmung + Vorschlag
        item {
            MoodSuggestionCard(
                navController = navController,
                viewModel = viewModel
            )
        }

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

        // Journal Button
        item {
            JournalButton {
                navController.navigate("journal")
            }
        }


        //Kosmische Meditation
        item {
            CosmicMeditationButton {
                navController.navigate("cosmic_meditation")
            }
        }

        //Alle Meditations-Kategorien
        items(categories) { item ->
            MeditationCard(item = item) {
                navController.navigate("player/${item.audioFile}/${item.imageResId}")
            }
        }
    }
}


