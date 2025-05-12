package de.syntax_institut.androidabschlussprojekt.presentation.screens.pose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.syntax_institut.androidabschlussprojekt.domain.model.MeditationPose
import de.syntax_institut.androidabschlussprojekt.presentation.theme.ElegantRed
import de.syntax_institut.androidabschlussprojekt.presentation.theme.NobleBlack
import de.syntax_institut.androidabschlussprojekt.presentation.theme.OceanBlue
import de.syntax_institut.androidabschlussprojekt.presentation.theme.SoftPurple
import mockMeditationPoses

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeditationPoseListScreen(onPoseClick: (Int) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Meditationshaltungen",
                        color = ElegantRed,
                        fontSize = 30.sp
                    )
                }
            )
        }

    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(mockMeditationPoses) { pose ->
                PoseListItem(pose) {
                    onPoseClick(pose.id)
                }
            }
        }
    }
}

@Composable
fun PoseListItem(pose: MeditationPose, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            Image(
                painter = painterResource(id = pose.imageRes),
                contentDescription = pose.name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 12.dp)
            )
            Column {
                Text(
                    pose.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = NobleBlack
                )
                Text(
                    pose.description,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 14.sp,
                    color = SoftPurple
                )
            }
        }
    }
}