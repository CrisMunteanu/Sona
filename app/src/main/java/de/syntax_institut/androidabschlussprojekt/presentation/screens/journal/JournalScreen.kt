package de.syntax_institut.androidabschlussprojekt.presentation.screens.journal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import de.syntax_institut.androidabschlussprojekt.presentation.theme.*
import de.syntax_institut.androidabschlussprojekt.presentation.viewmodel.JournalViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalScreen(
    navController: NavController,
    viewModel: JournalViewModel = koinViewModel()
) {
    var textState by remember { mutableStateOf(TextFieldValue("")) }
    val entries by viewModel.entries.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📝 Tagebuch", color = ElegantRed) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Zurück", tint = ElegantRed)
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Eingabefeld
            OutlinedTextField(
                value = textState,
                onValueChange = { textState = it },
                label = { Text("Neuer Eintrag") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 5
            )

            // Hinzufügen-Button
            Button(
                onClick = {
                    if (textState.text.isNotBlank()) {
                        viewModel.addEntry(textState.text.trim())
                        scope.launch {
                            snackbarHostState.showSnackbar("✅ Eintrag gespeichert")
                        }
                        textState = TextFieldValue("")
                    }
                },
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(containerColor = ElegantRed)
            ) {
                Text("Hinzufügen", color = VintageWhite)
            }

            HorizontalDivider(color = SoftPurple.copy(alpha = 0.3f))

            // Liste der Einträge
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(entries.reversed()) { entry ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = VintageWhite)
                    ) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = formatTimestamp(entry.timestamp),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = SoftPurple
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = entry.content,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = NobleBlack
                                )
                            }
                            IconButton(
                                onClick = {
                                    viewModel.deleteEntry(entry)
                                    scope.launch {
                                        snackbarHostState.showSnackbar("🗑️ Eintrag gelöscht")
                                    }
                                },
                                modifier = Modifier.align(Alignment.TopEnd)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Löschen",
                                    tint = ElegantRed
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// Hilfsfunktion zur Zeitformatierung
fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}