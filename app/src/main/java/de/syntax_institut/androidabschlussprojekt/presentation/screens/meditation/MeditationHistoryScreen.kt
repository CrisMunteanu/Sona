package de.syntax_institut.androidabschlussprojekt.presentation.screens.meditation

import android.view.inputmethod.DeleteGesture
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import de.syntax_institut.androidabschlussprojekt.presentation.theme.ElegantRed
import de.syntax_institut.androidabschlussprojekt.presentation.theme.SoftPurple
import de.syntax_institut.androidabschlussprojekt.presentation.theme.VintageWhite
import de.syntax_institut.androidabschlussprojekt.presentation.viewmodel.MeditationHistoryViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeditationHistoryScreen(
    navController: NavController,
    viewModel: MeditationHistoryViewModel = koinViewModel()
) {
    val history by viewModel.history.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🕓 Meditationsverlauf", color = ElegantRed) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Zurück",
                            tint = SoftPurple
                        )
                    }
                },
                actions = {
                    if (history.isNotEmpty()) {
                        IconButton(onClick = { viewModel.clearHistory() }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Alles löschen",
                                tint = ElegantRed
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (history.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Noch keine Meditationen abgeschlossen.",
                    color = SoftPurple.copy(alpha = 0.7f),
                    fontSize = 16.sp
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(history) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = VintageWhite)
                    ) {
                        ListItem(
                            headlineContent = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(
                                            text = item.title,
                                            color = ElegantRed,
                                            fontSize = 17.sp
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text("🎧 Datei: ${item.fileName}", fontSize = 13.sp)
                                        Text("⏱️ Dauer: ${item.duration}s", fontSize = 13.sp)
                                    }
                                    IconButton(onClick = { viewModel.deleteHistoryItem(item) }) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Eintrag löschen",
                                            tint = ElegantRed
                                        )
                                    }
                                }
                            },
                            supportingContent = null,
                            trailingContent = {
                                Text(
                                    text = SimpleDateFormat("dd.MM.yyyy\nHH:mm", Locale.getDefault())
                                        .format(Date(item.timestamp)),
                                    fontSize = 12.sp,
                                    lineHeight = 14.sp,
                                    color = SoftPurple
                                )
                            },
                            modifier = Modifier.clickable {
                                navController.navigate("player/${item.fileName}/0") // Dummy imageResId
                            }
                        )
                    }
                }
            }
        }
    }
}