package de.syntax_institut.androidabschlussprojekt.presentation.screens.pose

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.presentation.theme.ElegantRed
import de.syntax_institut.androidabschlussprojekt.presentation.theme.NobleBlack
import de.syntax_institut.androidabschlussprojekt.presentation.theme.SoftPurple
import mockMeditationPoses

import androidx.compose.ui.layout.ContentScale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeditationPoseDetailScreen(poseId: Int) {
    val pose = mockMeditationPoses.find { it.id == poseId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(pose?.name ?: "Haltung", color = ElegantRed)
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (pose != null) {
                // Rundes Bild
                Image(
                    painter = painterResource(id = pose.imageRes),
                    contentDescription = pose.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(240.dp)
                        .aspectRatio(1f)
                        .border(1.dp,SoftPurple,CircleShape)
                        .clip(CircleShape)
                        .padding(2.dp)
                )

                // Beschreibung
                Text(
                    text = pose.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = SoftPurple
                )

                // Lange Beschreibung
                Text(
                    text = pose.longDescription ?: "Diese Haltung unterstützt die innere Ausrichtung und fördert die Stabilität während der Meditation. Achte auf einen geraden Rücken und entspannten Atem.",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp),
                    color = NobleBlack
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Logo rund und zentriert
                Image(
                    painter = painterResource(id = R.drawable.logo_sona),
                    contentDescription = "Sona Logo",
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                )
            } else {
                Text("❗ Haltung nicht gefunden.", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}