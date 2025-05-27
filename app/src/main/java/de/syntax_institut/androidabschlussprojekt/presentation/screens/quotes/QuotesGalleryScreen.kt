package de.syntax_institut.androidabschlussprojekt.presentation.screens.quotes

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import de.syntax_institut.androidabschlussprojekt.data.local.entity.FavoriteQuoteEntity
import de.syntax_institut.androidabschlussprojekt.presentation.theme.ElegantRed
import de.syntax_institut.androidabschlussprojekt.presentation.theme.OceanBlue
import de.syntax_institut.androidabschlussprojekt.presentation.viewmodel.FavoriteQuoteViewModel
import de.syntax_institut.androidabschlussprojekt.presentation.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuotesGalleryScreen(
    navController: NavController,
    viewModel: MainViewModel = koinViewModel(),
    favoriteQuoteViewModel: FavoriteQuoteViewModel = koinViewModel()
) {
    val quotes by viewModel.quotes.collectAsState()
    val favorites by favoriteQuoteViewModel.favoriteQuotes.collectAsState()
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

    LaunchedEffect(Unit) {
        viewModel.loadAllQuotes()
    }

    SwipeRefresh(
        state = swipeState,
        onRefresh = {
            isRefreshing = true
            viewModel.loadAllQuotes()
            isRefreshing = false
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // 🔘 NEUER BUTTON
            Button(
                onClick = { navController.navigate("favorite_quotes") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ElegantRed)
            ) {
                Text(
                    text = "Lieblingszitate anzeigen",
                    color = Color.White
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(quotes) { quote ->
                    val isFavorite =
                        favorites.any { it.text == quote.text && it.author == quote.author }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFF8F3)
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "“${quote.text}”",
                                color = ElegantRed,
                                fontSize = 18.sp,
                                style = MaterialTheme.typography.bodyLarge
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "- ${quote.author}",
                                    color = OceanBlue.copy(alpha = 0.9f),
                                    fontSize = 16.sp,
                                    style = MaterialTheme.typography.labelMedium,
                                    modifier = Modifier.clickable {
                                        val encodedText = Uri.encode(quote.text)
                                        val encodedAuthor = Uri.encode(quote.author)
                                        navController.navigate("quote_detail/$encodedText/$encodedAuthor")
                                    }
                                )

                                IconButton(onClick = {
                                    val entity = FavoriteQuoteEntity(
                                        text = quote.text,
                                        author = quote.author,
                                        authorInfo = quote.authorInfo,
                                        authorImageResId = quote.authorImageResId
                                    )
                                    if (isFavorite) favoriteQuoteViewModel.removeFavorite(entity)
                                    else favoriteQuoteViewModel.addFavorite(entity)
                                }) {
                                    Icon(
                                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                        contentDescription = "Favorit",
                                        tint = if (isFavorite) ElegantRed else Color.Gray
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