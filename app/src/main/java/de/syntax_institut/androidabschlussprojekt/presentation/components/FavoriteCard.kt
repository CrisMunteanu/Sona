package de.syntax_institut.androidabschlussprojekt.presentation.components

import android.media.MediaMetadataRetriever
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    onClick: () -> Unit
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
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = item.imageResId),
                contentDescription = item.title,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 12.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = NobleBlack
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Dauer: ${durationText ?: "..."}",
                    style = MaterialTheme.typography.bodySmall,
                    color = OceanBlue
                )
            }

            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Favorit",
                tint = ElegantRed,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}