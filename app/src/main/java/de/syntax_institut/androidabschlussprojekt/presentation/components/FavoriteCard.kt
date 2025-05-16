package de.syntax_institut.androidabschlussprojekt.presentation.components

import android.media.MediaMetadataRetriever
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.domain.model.MeditationItem
import de.syntax_institut.androidabschlussprojekt.presentation.theme.ElegantRed
import de.syntax_institut.androidabschlussprojekt.presentation.theme.NobleBlack
import de.syntax_institut.androidabschlussprojekt.presentation.theme.OceanBlue

@Composable
fun FavoriteCard(
    item: MeditationItem,
    onClick: () -> Unit,
    onFavoriteToggle: () -> Unit
) {
    val context = LocalContext.current
    var durationText by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(item.audioFile) {
        val retriever = MediaMetadataRetriever()
        try {
            val afd = context.assets.openFd(item.audioFile)
            retriever.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            val durationMs = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLongOrNull()
            durationMs?.let {
                val minutes = it / 1000 / 60
                val seconds = (it / 1000 % 60).toString().padStart(2, '0')
                durationText = "$minutes:$seconds Min"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            durationText = "Unbekannt"
        } finally {
            retriever.release()
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Bild
            Image(
                painter = painterResource(id = item.imageResId),
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Titel + Dauer
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = NobleBlack,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Dauer: ${durationText ?: "..."}",
                    style = MaterialTheme.typography.bodySmall,
                    color = OceanBlue
                )
            }

            // Favoriten-Icon (Klickbar)
            IconButton(
                onClick = onFavoriteToggle
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Entfernen",
                    tint = ElegantRed,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}