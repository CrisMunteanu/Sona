package de.syntax_institut.androidabschlussprojekt.presentation.screens.pose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.presentation.theme.ElegantRed
import de.syntax_institut.androidabschlussprojekt.presentation.theme.OceanBlue
import de.syntax_institut.androidabschlussprojekt.presentation.theme.SoftPurple
import mockMeditationPoses

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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (pose != null) {
                Image(
                    painter = painterResource(id = pose.imageRes),
                    contentDescription = pose.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                )
                Text(
                    text = pose.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = SoftPurple
                )
            } else {
                Text("❗ Haltung nicht gefunden.", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}