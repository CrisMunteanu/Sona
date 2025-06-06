package de.syntax_institut.androidabschlussprojekt.presentation.screens.cosmic

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import de.syntax_institut.androidabschlussprojekt.presentation.theme.ElegantRed
import de.syntax_institut.androidabschlussprojekt.presentation.theme.NobleBlack
import org.koin.androidx.compose.koinViewModel

@Composable
fun CosmicMeditationScreen(
    navController: NavController,
    viewModel: CosmicMeditationViewModel = koinViewModel()
) {
    val cosmicMeditation by viewModel.cosmicMeditation.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    when {
        isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = error ?: "Unbekannter Fehler", color = ElegantRed)
            }
        }

        cosmicMeditation != null -> {
            val meditation = cosmicMeditation!!

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(meditation.imageUrl)
                            .crossfade(true)
                            .build()
                    ),
                    contentDescription = meditation.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = meditation.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = NobleBlack
                )

                Text(
                    text = meditation.explanation,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        navController.navigate("playerOnline/${Uri.encode(meditation.audioFile)}" +
                                "/${Uri.encode(meditation.imageUrl)}/${Uri.encode(meditation.title)}")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ElegantRed)
                ) {
                    Text(text = "Starte Meditation")
                }
            }
        }

        else -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Keine Daten verfügbar")
            }
        }
    }
}