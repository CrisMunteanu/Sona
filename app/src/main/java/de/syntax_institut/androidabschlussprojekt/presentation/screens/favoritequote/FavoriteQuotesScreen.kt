package de.syntax_institut.androidabschlussprojekt.presentation.screens.favoritequote

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import de.syntax_institut.androidabschlussprojekt.presentation.theme.*
import de.syntax_institut.androidabschlussprojekt.presentation.viewmodel.FavoriteQuoteViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteQuotesScreen(
    navController: NavController,
    viewModel: FavoriteQuoteViewModel = koinViewModel()
) {
    val favorites by viewModel.favoriteQuotes.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("❤️ Lieblingszitate", color = ElegantRed) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Zurück", tint = ElegantRed)
                    }
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
            if (favorites.isEmpty()) {
                Text("Keine Lieblingszitate vorhanden.", color = SoftPurple)
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(favorites) { quote ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = VintageWhite)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("\"\${quote.text}\"", color = SoftPurple)
                                Text("- \${quote.author}", color = ElegantRed)

                                IconButton(
                                    onClick = { viewModel.removeFavorite(quote) },
                                    modifier = Modifier.align(Alignment.End)
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
}



