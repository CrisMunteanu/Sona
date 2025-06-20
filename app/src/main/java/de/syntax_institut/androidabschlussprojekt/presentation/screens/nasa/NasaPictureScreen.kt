package de.syntax_institut.androidabschlussprojekt.presentation.screens.nasa

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.presentation.theme.ElegantRed
import de.syntax_institut.androidabschlussprojekt.presentation.theme.SoftPurple
import org.koin.androidx.compose.koinViewModel

@Composable
fun NasaPictureScreen(
    viewModel: NasaPictureViewModel = koinViewModel()
) {
    val picture by viewModel.nasaPicture.collectAsState()
    val error by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            error != null -> {
                Text(
                    text = error ?: "Unbekannter Fehler",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            picture != null -> {
                val image = picture!!

                // Scrollbarer Inhalt mit Logo unten
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
                                .data(image.url)
                                .crossfade(true)
                                .build()
                        ),
                        contentDescription = image.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = image.title,
                        style = MaterialTheme.typography.headlineSmall,
                        color = ElegantRed
                    )

                    Text(
                        text = image.date,
                        style = MaterialTheme.typography.bodySmall,
                        color = SoftPurple
                    )

                    Text(
                        text = image.explanation,
                        style = MaterialTheme.typography.bodyMedium
                    )


                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_sona),
                            contentDescription = "Sona Logo",
                            modifier = Modifier
                                .size(180.dp)
                                .clip(CircleShape)
                        )
                    }
                }
            }
        }
    }
}