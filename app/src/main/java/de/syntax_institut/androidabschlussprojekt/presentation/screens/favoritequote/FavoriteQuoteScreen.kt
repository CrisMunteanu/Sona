package de.syntax_institut.androidabschlussprojekt.presentation.screens.favoritequote

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import de.syntax_institut.androidabschlussprojekt.presentation.theme.*
import de.syntax_institut.androidabschlussprojekt.presentation.viewmodel.FavoriteQuoteViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FavoriteQuoteScreen(
    navController: NavController,
    viewModel: FavoriteQuoteViewModel = koinViewModel()
) {
    val favorites by viewModel.favoriteQuotes.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var sortOption by remember { mutableStateOf("Autor") }

    val sortedFavorites = when (sortOption) {
        "Autor" -> favorites.sortedBy { it.author }
        "Text" -> favorites.sortedBy { it.text }
        else -> favorites
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text("Lieblingszitate", color = ElegantRed)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Zurück", tint = ElegantRed)
                    }
                },
                actions = {
                    var expanded by remember { mutableStateOf(false) }

                    Box {
                        IconButton(onClick = { expanded = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Mehr Optionen", tint = ElegantRed)
                        }
                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            DropdownMenuItem(
                                text = { Text("Nach Autor sortieren") },
                                onClick = {
                                    sortOption = "Autor"
                                    expanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Nach Text sortieren") },
                                onClick = {
                                    sortOption = "Text"
                                    expanded = false
                                }
                            )
                            if (favorites.isNotEmpty()) {
                                DropdownMenuItem(
                                    text = { Text("Alle löschen") },
                                    onClick = {
                                        expanded = false
                                        scope.launch {
                                            val confirmed = snackbarHostState.showSnackbar(
                                                message = "Alle Zitate löschen?",
                                                actionLabel = "Löschen"
                                            )
                                            if (confirmed == SnackbarResult.ActionPerformed) {
                                                viewModel.clearAllFavorites()
                                                snackbarHostState.showSnackbar("Alle gelöscht")
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Keine Lieblingszitate vorhanden.", color = SoftPurple)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(sortedFavorites, key = { it.id }) { quote ->
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                                scope.launch {
                                    viewModel.removeFavorite(quote)
                                    snackbarHostState.showSnackbar("Zitat gelöscht")
                                }
                                true
                            } else false
                        }
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
                        background = {},
                        dismissContent = {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = VintageWhite),
                                elevation = CardDefaults.cardElevation(6.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text("“${quote.text}”", color = SoftPurple)
                                    Text("- ${quote.author}", color = ElegantRed)

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 8.dp),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        IconButton(onClick = {
                                            val text = "✨ Mein Lieblingszitat:\n\n“${quote.text}”\n- ${quote.author}"
                                            val sendIntent = Intent().apply {
                                                action = Intent.ACTION_SEND
                                                putExtra(Intent.EXTRA_TEXT, text)
                                                type = "text/plain"
                                            }
                                            context.startActivity(Intent.createChooser(sendIntent, "Teilen mit..."))
                                        }) {
                                            Icon(Icons.Default.Share, contentDescription = "Teilen", tint = OceanBlue)
                                        }

                                        IconButton(onClick = {
                                            scope.launch {
                                                viewModel.removeFavorite(quote)
                                                snackbarHostState.showSnackbar("Zitat gelöscht")
                                            }
                                        }) {
                                            Icon(Icons.Default.Delete, contentDescription = "Löschen",
                                                tint = ElegantRed)
                                        }
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}