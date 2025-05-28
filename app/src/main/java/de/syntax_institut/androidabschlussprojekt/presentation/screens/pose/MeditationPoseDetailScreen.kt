package de.syntax_institut.androidabschlussprojekt.presentation.screens.pose

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
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
import androidx.navigation.NavController
import de.syntax_institut.androidabschlussprojekt.presentation.theme.VintageWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeditationPoseDetailScreen(poseId: Int, navController: NavController) {
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

                Image(
                    painter = painterResource(id = pose.imageRes),
                    contentDescription = pose.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(240.dp)
                        .aspectRatio(1f)
                        .border(2.dp, ElegantRed.copy(alpha = 0.4f), CircleShape)
                        .clip(CircleShape)
                        .padding(2.dp)
                )

                Text(
                    text = pose.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = SoftPurple
                )

                Text(
                    text = pose.longDescription ?: "Diese Haltung unterstützt die innere Ausrichtung...",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp),
                    color = NobleBlack
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Schöner Button
                Button(
                    onClick = { navController.navigate("meditation_history") },
                    colors = ButtonDefaults.buttonColors(containerColor = ElegantRed),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = "Verlauf",
                        tint = VintageWhite
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Meditationsverlauf anzeigen", color = VintageWhite)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Image(
                    painter = painterResource(id = R.drawable.logo_sona),
                    contentDescription = "Sona Logo",
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                )
            } else {
                Text("❗ Haltung nicht gefunden.", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}