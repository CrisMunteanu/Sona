package de.syntax_institut.androidabschlussprojekt.presentation.screens.pose

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import de.syntax_institut.androidabschlussprojekt.presentation.components.YogaRadioCard
import de.syntax_institut.androidabschlussprojekt.presentation.theme.VintageWhite
import androidx.core.net.toUri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeditationPoseDetailScreen(poseId: Int, navController: NavController) {
    val pose = mockMeditationPoses.find { it.id == poseId }
    val context = LocalContext.current
    val expanded = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

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
                .verticalScroll(scrollState)
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (pose != null) {

                // Bild
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

                //Kurzbeschreibung
                Text(
                    text = pose.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = SoftPurple
                )

                //Lange Beschreibung (ausklappbar)
                Text(
                    text = if (expanded.value) pose.longDescription else pose.longDescription.take(200) + "...",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                    color = NobleBlack
                )

                TextButton(onClick = { expanded.value = !expanded.value }) {
                    Text(
                        text = if (expanded.value) "Weniger anzeigen" else "Mehr anzeigen",
                        color = ElegantRed
                    )
                }

                // YouTube-Button mit Fallback
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(pose.youtubeUrl))
                        intent.setPackage("com.google.android.youtube")
                        if (intent.resolveActivity(context.packageManager) != null) {
                            context.startActivity(intent)
                        } else {
                            // Fallback: Öffne im Browser
                            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(pose.youtubeUrl))
                            context.startActivity(browserIntent)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ElegantRed),
                    shape = RoundedCornerShape(20),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "YouTube Video",
                        tint = VintageWhite
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("YouTube-Video zur Haltung", color = VintageWhite)
                }

                // Verlauf anzeigen
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

                //Yoga-Radio
                YogaRadioCard {
                    navController.navigate("yoga_radio")
                }

                //Sona-Logo
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