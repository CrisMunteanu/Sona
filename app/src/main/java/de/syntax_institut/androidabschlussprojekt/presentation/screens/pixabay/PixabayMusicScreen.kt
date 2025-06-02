package de.syntax_institut.androidabschlussprojekt.presentation.screens.pixabay

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.presentation.viewmodel.PixabayMusicViewModel
import org.koin.androidx.compose.koinViewModel
import de.syntax_institut.androidabschlussprojekt.domain.model.PixabayMusicTrack
import de.syntax_institut.androidabschlussprojekt.presentation.components.TrackCard
import de.syntax_institut.androidabschlussprojekt.presentation.theme.ElegantRed
import de.syntax_institut.androidabschlussprojekt.presentation.theme.SoftPurple


@Composable
fun PixabayMusicScreen(
    viewModel: PixabayMusicViewModel = koinViewModel(),
    onPlayTrack: (PixabayMusicTrack) -> Unit
) {
    val tracks = viewModel.tracks.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "🎵 Entspannungsmusik",
            style = MaterialTheme.typography.headlineSmall,
            color = ElegantRed
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (isLoading.value) {
            CircularProgressIndicator(color = SoftPurple)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(tracks.value) { track ->
                    TrackCard(track = track, onClick = { onPlayTrack(track) })
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { viewModel.loadPreviousPage() }, enabled = viewModel.canGoBack()) {
                Text("Zurück")
            }
            Button(onClick = { viewModel.loadNextPage() }) {
                Text("Weiter")
            }
        }
    }
}