package de.syntax_institut.androidabschlussprojekt.presentation.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import de.syntax_institut.androidabschlussprojekt.presentation.components.MeditationCard
import de.syntax_institut.androidabschlussprojekt.presentation.theme.ElegantRed
import de.syntax_institut.androidabschlussprojekt.presentation.theme.SoftPurple
import de.syntax_institut.androidabschlussprojekt.presentation.theme.VintageWhite
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
        item {
            QuoteGalleryCard(
                onClick = { navController.navigate("quotes") }
            )
        }

        items(categories) { item ->
            MeditationCard(item = item) {
                navController.navigate("player/${item.audioFile}/${item.imageResId}")
            }
        }
    }
}

@Composable
fun QuoteGalleryCard(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF4E7CD)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.FormatQuote,
                contentDescription = "Zitat Icon",
                tint = VintageWhite,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "Zitaten Galerie",
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 30.sp),
                    color = ElegantRed
                )
                Text(
                    text = "Inspiration für deinen Tag",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SoftPurple
                )
            }
        }
    }
}