package de.syntax_institut.androidabschlussprojekt.presentation.screens.journal

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import de.syntax_institut.androidabschlussprojekt.presentation.theme.*
import de.syntax_institut.androidabschlussprojekt.presentation.viewmodel.JournalViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.time.Instant
import java.time.ZoneId
import java.util.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import java.time.*
import java.time.format.DateTimeFormatter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun JournalScreen(
    navController: NavController,
    viewModel: JournalViewModel = koinViewModel()
) {
    var textState by remember { mutableStateOf(TextFieldValue("")) }
    val entries by viewModel.entries.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    // Tage des Monats + leere Felder
    val daysInMonth = remember(currentMonth) {
        val firstDayOfWeek = currentMonth.atDay(1).dayOfWeek.value % 7
        val totalDays = currentMonth.lengthOfMonth()
        val list = mutableListOf<LocalDate?>()

        repeat(firstDayOfWeek) { list.add(null) }
        for (day in 1..totalDays) {
            list.add(currentMonth.atDay(day))
        }
        while (list.size < 42) { // Auffüllen auf 6 Wochen (6x7)
            list.add(null)
        }
        list
    }

    val filteredEntries = remember(selectedDate, entries) {
        selectedDate?.let { date ->
            entries.filter { entry ->
                val entryDate = Instant.ofEpochMilli(entry.timestamp)
                    .atZone(ZoneId.systemDefault()).toLocalDate()
                entryDate == date
            }
        } ?: entries
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tagebuch :", color = ElegantRed) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Zurück", tint = ElegantRed)
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // Monatsnavigation
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { currentMonth = currentMonth.minusMonths(1) }) {
                        Text("<", fontSize = 20.sp)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault())),
                            style = MaterialTheme.typography.titleMedium
                        )
                        selectedDate?.let {
                            TextButton(onClick = { selectedDate = null }) {
                                Text("📅 Filter entfernen", color = SoftPurple)
                            }
                        }
                    }
                    IconButton(onClick = { currentMonth = currentMonth.plusMonths(1) }) {
                        Text(">", fontSize = 20.sp)
                    }
                }
            }

            item {
                // Wochentage
                val weekdays = listOf("Mo", "Di", "Mi", "Do", "Fr", "Sa", "So")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    weekdays.forEach { day ->
                        Text(
                            text = day,
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.labelSmall,
                            color = SoftPurple,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            item {
                // Kalender (immer 6 Reihen)
                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(getCalendarHeight()),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    userScrollEnabled = false
                ) {
                    items(daysInMonth) { day ->
                        val hasEntry = entries.any {
                            val entryDate = Instant.ofEpochMilli(it.timestamp)
                                .atZone(ZoneId.systemDefault()).toLocalDate()
                            entryDate == day
                        }
                        val isSelected = selectedDate == day

                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    when {
                                        day == null -> Color.Transparent
                                        isSelected -> ElegantRed
                                        hasEntry -> SoftPurple.copy(alpha = 0.3f)
                                        else -> MaterialTheme.colorScheme.surface
                                    }
                                )
                                .clickable(enabled = day != null) { selectedDate = day },
                            contentAlignment = Alignment.Center
                        ) {
                            day?.let {
                                Text(
                                    text = it.dayOfMonth.toString(),
                                    color = if (isSelected) VintageWhite else NobleBlack
                                )
                            }
                        }
                    }
                }
            }

            item {
                // Eintrag Textfeld
                OutlinedTextField(
                    value = textState,
                    onValueChange = { textState = it },
                    label = { Text("Neuer Eintrag") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 5
                )
            }

            item {
                // Hinzufügen Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
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
                        colors = ButtonDefaults.buttonColors(containerColor = ElegantRed)
                    ) {
                        Text("Hinzufügen", color = VintageWhite)
                    }
                }
            }

            item {
                HorizontalDivider(color = SoftPurple.copy(alpha = 0.3f))
            }

            items(filteredEntries.reversed()) { entry ->
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
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.TopEnd)
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

fun getCalendarHeight(): Dp = 48.dp * 6

fun formatTimestamp(timestamp: Long): String {
    val sdf = java.text.SimpleDateFormat("dd.MM.yyyy HH:mm", java.util.Locale.getDefault())
    return sdf.format(java.util.Date(timestamp))
}