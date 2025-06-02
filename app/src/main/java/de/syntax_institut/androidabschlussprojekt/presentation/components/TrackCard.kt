package de.syntax_institut.androidabschlussprojekt.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import de.syntax_institut.androidabschlussprojekt.domain.model.PixabayMusicTrack
import de.syntax_institut.androidabschlussprojekt.presentation.theme.ElegantRed
import de.syntax_institut.androidabschlussprojekt.presentation.theme.SoftPurple


@Composable
fun TrackCard(
    track: PixabayMusicTrack,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(track.coverUrl),
                contentDescription = "Cover",
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 12.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = track.title.ifBlank { "🎵 Titel unbekannt" },
                    style = MaterialTheme.typography.titleMedium,
                    color = ElegantRed
                )
                Text(
                    text = "👤 ${track.artist.ifBlank { "Unbekannter Künstler" }}",
                    style = MaterialTheme.typography.bodySmall,
                    color = SoftPurple
                )
            }

            Text(
                text = "${track.duration}s",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}