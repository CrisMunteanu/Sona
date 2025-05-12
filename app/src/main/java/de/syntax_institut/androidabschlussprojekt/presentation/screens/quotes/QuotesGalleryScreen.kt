package de.syntax_institut.androidabschlussprojekt.presentation.screens.quotes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import de.syntax_institut.androidabschlussprojekt.presentation.theme.ElegantRed
import de.syntax_institut.androidabschlussprojekt.presentation.theme.OceanBlue
import de.syntax_institut.androidabschlussprojekt.presentation.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuotesGalleryScreen(
    viewModel: MainViewModel = koinViewModel()
) {
    val quotes by viewModel.quotes.collectAsState()
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(quotes) { quote ->
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
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "“${quote.text}”",
                            color = ElegantRed,
                            fontSize = 18.sp,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "- ${quote.author}",
                            color = OceanBlue.copy(alpha = 0.9f),
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}